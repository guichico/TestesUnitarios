package com.br.guilherme.views;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.br.guilherme.entities.Movie;
import com.br.guilherme.service.MovieService;

public class MainScreen {
	
	static MovieService movieService;

	public static void main(String[] args) {
		/*Movie movie1 = Movie
				.builder()
				.withTitle("Senhor dos Anéis")
				.withCoverImg("https://http2.mlstatic.com/blu-ray-o-senhor-dos-aneis-a-sociedade-do-anel-dublado-D_NQ_NP_710585-MLB27144207263_042018-F.jpg")
				.withStock(2)
				.withPrice(5.0)
				.build(), 
				movie2 =  Movie
				.builder()
				.withTitle("Poderoso Chefão")
				.withCoverImg("https://http2.mlstatic.com/blu-ray-o-poderoso-chefo-D_NQ_NP_280325-MLB25409924107_032017-F.jpg")
				.withStock(3)
				.withPrice(4.5)
				.build(),
				movie3 =  Movie
				.builder()
				.withTitle("Vingadores")
				.withCoverImg("https://images.livrariasaraiva.com.br/imagemnet/imagem.aspx/?pro_id=4071290&qld=90&l=430&a=-1=1000621175")
				.withStock(10)
				.withPrice(9.0)
				.build(),
				movie4 =  Movie
				.builder()
				.withTitle("Pocahontas")
				.withCoverImg("https://http2.mlstatic.com/blu-ray-pocahontas-legendado-lacrado-D_NQ_NP_629930-MLB27375221378_052018-F.jpg")
				.withStock(1)
				.withPrice(3.0)
				.build();		
		List<Movie> movies = Arrays.asList(movie1, movie2, movie3, movie4);*/

		//List<Movie> movies = movieService.getMovies();
		
		RegisterUserScreen registerUserScreen = new RegisterUserScreen("Cadastrar usuário");
		registerUserScreen.showScreen();
		
		//createGui(createMoviewGrid(movies), "Locadora");
	}

	private static void createGui(JPanel contents, String title){
		JFrame applicationFrame = new JFrame(title);
		applicationFrame.getContentPane().add(contents);
		applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		applicationFrame.pack();
		applicationFrame.setSize(1280, 800);
		applicationFrame.setLocationRelativeTo(null);
		applicationFrame.setVisible(true);
	}

	private static JPanel createMoviewGrid(List<Movie> movies) {
		JPanel panel = new JPanel(new GridLayout(2, 2));

		for (Movie movie : movies) {
			JLabel label = new JLabel();
			panel.add(label);

			try {
				BufferedImage croppedImage = ImageIO.read(new URL(movie.getCoverImg()));
				label.setIcon(new ImageIcon(croppedImage));
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}		

		return panel;
	}
}