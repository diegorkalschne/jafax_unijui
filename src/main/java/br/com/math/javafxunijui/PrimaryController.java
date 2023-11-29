package br.com.math.javafxunijui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PrimaryController implements Initializable {

    @FXML
    private ImageView imageView; //ID definido no ImageView criado no Scene Builder

    /**
     * Método executado na inicialização da tela Está sendo utilizado para
     * carregar a logo da Unijuí
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Image image = new Image(getClass().getResourceAsStream("/images/logo.png")); //Carregando a imagem a partir dos recursos do projeto
            imageView.setImage(image); //Exibindo a imagem
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Método utilizado para ir para a segudan view
     *
     * @throws IOException
     */
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    /**
     * Método utilizado para exibir um Alert ao usuário
     */
    @FXML
    private void showMessage() {
        //Substituindo o JOptionPane no JavaFX com Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Atenção");
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.setContentText("Você clicou no botão");
        alert.showAndWait();
    }
}
