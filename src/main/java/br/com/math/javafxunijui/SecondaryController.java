package br.com.math.javafxunijui;

import com.gluonhq.charm.glisten.control.TextField;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import org.json.simple.JSONObject;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

public class SecondaryController implements Initializable {

    private final String VIACEP_API = "https://viacep.com.br/ws/";

    @FXML
    private Label ufLabel;
    @FXML
    private Label dddLabel;
    @FXML
    private Label localidadeLabel;
    @FXML
    private Label ibgeLabel;
    @FXML
    private Label logradouroLabel;
    @FXML
    private Label bairroLabel;
    @FXML
    private TextField cepTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Listener para não deixar escrever letras no campo de texto (apenas números)
        cepTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                Platform.runLater(() -> {
                    cepTextField.setText(oldValue);
                });
            }

            // Não permitir textos maiores que 8 números
            if (newValue.length() > 8) {
                Platform.runLater(() -> {
                    cepTextField.setText(oldValue);
                });
            }
        });
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void onSearch() {
        String cep = cepTextField.getText();

        if (cep.length() != 8) {
            showAlert("Atenção", "CEP incorreto. Certifique-se que ele tenha 8 dígitos");
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog("Atenção", "Carregando...");

        Task<JSONObject> task = new Task<JSONObject>() {
            @Override
            protected JSONObject call() throws Exception {
                return fetchCep(cep + "/json");
            }
        };

        task.setOnSucceeded(event -> {
            JSONObject response = task.getValue();
            ufLabel.setText((String) response.get("uf"));
            dddLabel.setText((String) response.get("ddd"));
            localidadeLabel.setText((String) response.get("localidade"));
            ibgeLabel.setText((String) response.get("ibge"));
            logradouroLabel.setText((String) response.get("logradouro"));
            bairroLabel.setText((String) response.get("bairro"));

            progressDialog.close();
        });

        task.setOnFailed(event -> {
            showAlert("Atenção", "Ocorreu um erro ao tentar pesquisar o CEP");
            progressDialog.close();
        });

        progressDialog.show();

        new Thread(task).start();
    }

    private JSONObject fetchCep(String woeid) throws MalformedURLException {
        HttpService apiService = new HttpService(VIACEP_API);

        JSONObject response = apiService.getJSONObject(woeid);

        return response;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.setContentText(message);
        alert.show();
    }

    private class ProgressDialog extends Stage {

        public ProgressDialog(String title, String description) {
            initModality(Modality.APPLICATION_MODAL);
            setResizable(false);
            setTitle(title);

            // Definindo o ícone inicial do app
            Image image = new Image(getClass().getResourceAsStream("/images/icon.png"));
            getIcons().add(image);

            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefWidth(200);

            VBox vBox = new VBox(new Label(description), progressBar);
            vBox.setSpacing(10);
            vBox.setAlignment(javafx.geometry.Pos.CENTER);

            setScene(new javafx.scene.Scene(vBox));
        }
    }
}
