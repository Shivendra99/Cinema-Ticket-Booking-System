package com.cinema_movie_api.main.services;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

import com.cinema_movie_api.main.entities.CinemaHall;
import com.cinema_movie_api.main.entities.CinemaMovieShows;
import com.cinema_movie_api.main.entities.Movie;
import com.cinema_movie_api.main.repositories.CinemaMovieRepository;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

@Service
public class CinemaMovieShowService{
	
	@Autowired
	private CinemaMovieRepository cinemaMovieRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectFactory<ShowAPICallHystrixCommand> remoteAPIFactory;
	
	@Autowired
	private HystrixCommand.Setter config;
	
	public <T> ShowAPICallHystrixCommand<T> getRemoteAPIFactoryHystrixCommandInstance() {
		HystrixCommand.Setter config = HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteAPICallGroup"));
		return null;
	}
	
	
	//@Autowired
	//private WebClient.Builder webClient;
	
	public CinemaMovieShows getShowById(int aShowId) {
		try {
			return cinemaMovieRepository.getShowById(aShowId);
		}catch(Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public List<CinemaMovieShows> getFilteredShowList(Date date, String aCinemaId){
		try {
			List<CinemaMovieShows> showList= cinemaMovieRepository.getFilteredShowList(date, aCinemaId);
			return showList;
		}catch(Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public boolean addShowList(List<CinemaMovieShows> aShowList, List<String> aCinemaIdList, List<String> aMovieIdList) {
		try {
			
			Future<List> cinemaApiResponse= makeGetCall(API_URL.HTTP.toString()+ API_URL.CINEMA_API.toString()+ "/cinemaHallsIn/cinemaId", List.class, aCinemaIdList);
			Future<List> movieApiResponse= makeGetCall(API_URL.HTTP.toString()+ API_URL.MOVIE_API.toString()+ "/moviesIn/movieId", List.class, aMovieIdList);
			
			List<CinemaHall> cinemaHallList= cinemaApiResponse.get();
			List<Movie> movieList= movieApiResponse.get();
			
			if(cinemaHallList== null || movieList== null)
				return false;
			
			for(int i= 0; i< aShowList.size(); i++) {
				if(cinemaHallList.get(i)== null|| movieList.get(i)== null)
					continue;
				
				if(aShowList.get(i).getShowDate().before(movieList.get(i).getReleaseDate())) {
					aShowList.remove(i);
					continue;
				}
				
				aShowList.get(i).setCinemaHall(cinemaHallList.get(i));
				aShowList.get(i).setMovie(movieList.get(i));
			}
			
			return cinemaMovieRepository.addShowList(aShowList);
		}catch(Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}
	
	public String addUpdateShow(String aCinemaId, String aMovieId, CinemaMovieShows aShow, boolean isAdded) {
		try {
			
			/*CinemaHall myCinema= webClient.build()
					                      .get()
			                              .uri("http://localhost:8100/cinemaAPI/getCinemaHall/"+ aCinemaId)
			                              .retrieve()
			                              .bodyToMono(CinemaHall.class)
			                              .block();*/
			
			Future<CinemaHall> cinemaApiResponse= makeGetCall(API_URL.HTTP.toString()+ API_URL.CINEMA_API.toString()+ "/cinemaAPI/cinemaHall/"+ aCinemaId, CinemaHall.class);
			Future<Movie> movieApiResponse= makeGetCall(API_URL.HTTP.toString()+ API_URL.MOVIE_API.toString()+ "/movieAPI/movieById/"+ aMovieId, Movie.class);
			
			CinemaHall cinema= cinemaApiResponse.get();
			if(cinema== null)
				return "No Cinema Hall exists with ID: "+ aCinemaId;
			aShow.setCinemaHall(cinema);
			
			Movie movie= movieApiResponse.get();
			if(movie== null)
				return "No Movie exists with ID: "+ aMovieId;
			aShow.setMovie(movie);
			
			if(!aShow.getShowDate().before(movie.getReleaseDate())) {
				
				Integer id= (isAdded)? cinemaMovieRepository.addShow(aShow): cinemaMovieRepository.updateShow(aShow);
				
				if(id!= null) {
					return "Show with ID: "+ aShow.getEventId()+ ((isAdded)? " inserted.": " updated.");
				}else
					return "Show not" + ((isAdded)? " inserted.": " updated.");
				
			}else {
				return "Show date can't be prior to Movie release date.";
			}
		}catch(Exception exception) {
			exception.printStackTrace();
			return "Error in"+ ((isAdded)? " inserting": " updating")+ " show: "+ exception.toString();
		}
	}
	
	public boolean deleteShow(String eventId) {
		try {
			return cinemaMovieRepository.deleteShow(eventId);
		}catch(Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}
	
	@Async
	//This doesn't works well with classes having @Async annotations, use it when making synchronous API calls.
	//@HystrixCommand(fallbackMethod = "makeGetCallFallBack")
	private <T> Future<T> makeGetCall(String aUrl, Class<T> aResponseType, Object...aUriVariables) {
		
		Future<T> response=  new ShowAPICallHystrixCommand(config, restTemplate, aUrl, aResponseType, aUriVariables).queue();
		return response;
	}
	
//	/**
//	 * For each particular response type, specify fallback response here
//	 * 
//	 * @param <T>
//	 * @param aUrl
//	 * @param aResponseType
//	 * @param aUriVariables
//	 * @return
//	 */
//	@Async
//	private <T> CompletableFuture<T> makeGetCallFallBack(String aUrl, Class<T> aResponseType, Object...aUriVariables) {
//		
//		if(aResponseType == CinemaHall.class) {
//			CinemaHall cinemaHall= new CinemaHall();
//			cinemaHall.setCapacity(1);
//			cinemaHall.setCinemaId("NA");
//			cinemaHall.setCinemaName("NA");
//			cinemaHall.setCity("NA");
//			
//			return CompletableFuture.completedFuture((T)cinemaHall);
//		}
//		else if(aResponseType== Movie.class) {
//			Movie movie= new Movie();
//			movie.setMovieId("NA");
//			movie.setMovieJonre("Others");
//			movie.setMovieLanguage("NA");
//			movie.setMovieName("NA");
//			movie.setReleaseDate(new Date(0));
//			
//			return CompletableFuture.completedFuture((T)movie);
//		}
//		
//		return CompletableFuture.completedFuture(null);
//		return null;
//	}
	
}