package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.sql.SQLException;
import java.util.Random;

import static javafx.scene.paint.Color.*;

public class Main extends Application implements EventHandler<ActionEvent>{

    Button login,register,login_button,rsa_button,el_button,reg_submit,rsa_en_button,rsa_de_button,final_back_button,EL_en_button, rsa_back_button,login_back_button,EL_gamal_en_button,el_de_button ;
    Button Ciper_en_button, Ciper_de_button ;
    TextField login_name,login_password,reg_name,reg_password;

    private String filePath,user;
    Label Wrong ;
    private Stage window;
    private Scene start_scr,login_scr,register_scr,final_scr;
    Desktop desktop ;
    private FileChooser fileChooser;

    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;
        window.setTitle("FileLocking");
        window.setMinHeight(500);
        window.setMinWidth(500);

        // #################    Start Screen Start   ################## //


        login = new Button();
        login.setText("  Login  ");
        login.setTextFill(BLUE);
        login.setFont(Font.font(null,FontWeight.BOLD,15));
        login.setOnAction(this);

        register = new Button();
        register.setText("Register");
        register.setTextFill(BLUE);
        register.setFont(Font.font(null,FontWeight.BOLD,15));
        register.setOnAction(this);

        Text text = new Text("   Welcome to File Locking System   ");
        text.setFont(Font.font(null, FontWeight.BOLD, 30));
        text.setFill(Color.GREEN);
        text.setX(50);
        text.setY(40);

        VBox vBox = new VBox(15);
        vBox.getChildren().addAll(text,login,register);
        start_scr = new Scene(vBox,500,500);
        // #################    Start Screen End   ################## //


        // #################    Login Screen Start   ################## //


        Label label_login = new Label("              Login Here     ");

        label_login.setTextFill(Color.GREEN);
        label_login.setFont(Font.font(null, FontWeight.BOLD, 30));


        GridPane grid_login = new GridPane();
        grid_login.setPadding(new Insets(10, 10, 10, 10));
        grid_login.setVgap(5);
        grid_login.setHgap(5);

        // UserName
        login_name = new TextField();
        login_name.setPromptText("User Name");
        login_name.getText();
        login_name.setMaxSize(200,10);
        GridPane.setConstraints(login_name, 0, 0);
        grid_login.getChildren().add(login_name);

        // Password
        login_password = new TextField();
        login_password.setPromptText("Password");
        login_password.setPrefColumnCount(8);
        login_password.getText();
        login_password.setMaxSize(200,10);
        GridPane.setConstraints(login_password, 0, 1);
        grid_login.getChildren().add(login_password);

        // Login button
        login_button = new Button("LOGIN");
        GridPane.setConstraints(login_button, 1, 0);
        grid_login.getChildren().add(login_button);
        login_button.setOnAction(this);
        login_button.setTextFill(BLUE);
        login_button.setFont(Font.font(null,FontWeight.BOLD,15));

        // back button
        login_back_button = new Button(" BACK ");
        GridPane.setConstraints(login_back_button , 1, 3);
        grid_login.getChildren().add(login_back_button );
        login_back_button.setTextFill(BLUE);
        login_back_button.setOnAction(this);
        login_back_button.setFont(Font.font(null,FontWeight.BOLD,15));

        VBox vBox_login = new VBox(15);
        vBox_login.getChildren().addAll(label_login,login_name,login_password,login_button,login_back_button);
        login_scr = new Scene(vBox_login,300,300);


        // #################    Login Screen End   ################## //


        // #################    Registration Screen Start   ################## //


        Label label_reg = new Label("           Registration Here     ");
        label_reg.setTextFill(Color.GREEN);
        label_reg.setFont(Font.font(null, FontWeight.BOLD, 30));
        GridPane grid_reg = new GridPane();
        grid_reg.setPadding(new Insets(10, 10, 10, 10));
        grid_reg.setVgap(5);
        grid_reg.setHgap(5);

        // UserName
        reg_name = new TextField();
        reg_name.setPromptText("User Name");
        reg_name.setMaxSize(200,10);
        GridPane.setConstraints(reg_name, 0, 0);
        grid_reg.getChildren().add(reg_name);

        // Password
        reg_password = new TextField();
        reg_password.setPromptText("Password");
        reg_password.setMaxSize(200,10);
        reg_password.setPrefColumnCount(8);
        GridPane.setConstraints(reg_password, 0, 1);
        grid_reg.getChildren().add(reg_password);

        // RSA button
        reg_submit = new Button("SUBMIT");
        reg_submit.setTextFill(BLUE);
        reg_submit.setFont(Font.font(null, FontWeight.BOLD, 15));
        GridPane.setConstraints(reg_submit, 1, 2);
        grid_reg.getChildren().add(reg_submit);

        reg_submit.setOnAction(this);

        // back button
        rsa_back_button = new Button("  BACK  ");
        GridPane.setConstraints(rsa_back_button , 1, 3);
        grid_reg.getChildren().add(rsa_back_button );
        rsa_back_button.setTextFill(BLUE);
        rsa_back_button.setFont(Font.font(null, FontWeight.BOLD, 15));
        rsa_back_button.setOnAction(this);

        VBox vBox_reg = new VBox(15);
        vBox_reg.getChildren().addAll(label_reg,reg_name,reg_password,reg_submit, rsa_back_button);
        register_scr = new Scene(vBox_reg,300,300);

        // #################    Registration Screen End   ################## //




        // #################    Final Screen Start   ################## //


        Label final_label = new Label("Choose a Encryption Algorithm  ");
        final_label.setTextFill(Color.GREEN);
        final_label.setFont(Font.font(null,FontWeight.BOLD,20));

        desktop = Desktop.getDesktop();
        fileChooser = new FileChooser();

        Label FileLabel = new Label("Select a File");
        FileLabel.setTextFill(Color.GREEN);
        FileLabel.setFont(Font.font(null,FontWeight.BOLD,20));

        GridPane grid_final = new GridPane();
        grid_final.setPadding(new Insets(10, 10, 10, 10));
        grid_final.setVgap(5);
        grid_final.setHgap(5);

        final Button openButton = new Button(" BROWSE ");
        openButton.setTextFill(BLUE);
        openButton.setFont(Font.font(null, FontWeight.BOLD, 15));
        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(window);
                        filePath = file.getAbsolutePath();
                    }
                });
        GridPane.setConstraints(openButton, 0, 0);

        // Ciper encrytion
        Ciper_en_button = new Button("CIPHER ENCRYPTION");
        Ciper_en_button.setTextFill(BLACK);
        Ciper_en_button.setFont(Font.font(null,FontWeight.BOLD,15));
        GridPane.setConstraints(  Ciper_en_button, 1, 0);
        grid_final.getChildren().add(  Ciper_en_button);
        Ciper_en_button.setOnAction(this);

        // Ciper Decryption button
        Ciper_de_button = new Button("CIPHER DECRYPTION");
        Ciper_de_button.setTextFill(BLACK);
        Ciper_de_button.setFont(Font.font(null,FontWeight.BOLD,15));
        GridPane.setConstraints(Ciper_de_button, 1, 0);
        grid_final.getChildren().add(Ciper_de_button);
        Ciper_de_button.setOnAction(this);



        // RSA Encryption button
        rsa_en_button = new Button(" RSA ENCRYPTION ");
        GridPane.setConstraints(rsa_en_button, 1, 0);
        rsa_en_button.setTextFill(BROWN);
        rsa_en_button.setFont(Font.font(null, FontWeight.BOLD, 15));
        grid_final.getChildren().add(rsa_en_button);
        rsa_en_button.setOnAction(this);

        // RSA Decryption button
        rsa_de_button = new Button(" RSA DECRYPTION ");
        GridPane.setConstraints(rsa_de_button, 2, 0);
        rsa_de_button.setTextFill(BROWN);
        rsa_de_button.setFont(Font.font(null, FontWeight.BOLD, 15));
        grid_final.getChildren().add(rsa_de_button);
        rsa_de_button.setOnAction(this);




        //EL Button

        EL_en_button = new Button("EL GAMAl ENCRYPTION");
        EL_en_button.setLayoutX(5);
        EL_en_button.setLayoutY(5);
        GridPane.setConstraints(  EL_en_button, 1, 0);
        EL_en_button.setTextFill(DARKCYAN);
        EL_en_button.setFont(Font.font(null, FontWeight.BOLD, 15));
        grid_final.getChildren().add(  EL_en_button);
        EL_en_button.setOnAction(this);

        el_de_button = new Button("EL GAMAL DECRYPTION");
        GridPane.setConstraints(  el_de_button, 1, 0);
        el_de_button.setTextFill(DARKCYAN);
        el_de_button.setFont(Font.font(null, FontWeight.BOLD, 15));
        grid_final.getChildren().add(  el_de_button);
        el_de_button.setOnAction(this);

        // back button
        final_back_button = new Button("  BACK ");
        GridPane.setConstraints(final_back_button , 1, 3);
        final_back_button.setTextFill(BLUE);
        final_back_button.setFont(Font.font(null, FontWeight.BOLD, 15));
        grid_final.getChildren().add(final_back_button);
        final_back_button.setOnAction(this);

        VBox vBox_final = new VBox(5);
        vBox_final.getChildren().addAll(FileLabel,openButton,final_label, Ciper_en_button,Ciper_de_button,rsa_en_button,rsa_de_button, EL_en_button,el_de_button,final_back_button);
        final_scr = new Scene(vBox_final,500,500);


        // #################    Final Screen End   ################## //

        window.setScene(start_scr);
        window.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == login){
            rsa r = new rsa();
            window.setScene(login_scr);
        }
        else if (event.getSource() == register){
            window.setScene(register_scr);
        }
        else if (event.getSource() == login_button){
            //System.out.println(event.getSource());
            database db = new database();
            if (db.loginCheck(login_name.getText(),login_password.getText())){
                user = login_name.getText();
                //     System.out.println(user);
                window.setScene(final_scr);
            } else {

                Label Wrong = new Label();
                Wrong.setText("Wrong Username or Password !!!");
                Wrong.setTextFill(Color.RED);

                // System.out.print("Wrong Username or Password !!!");

            }
        }
        else if (event.getSource() == reg_submit){

            System.out.println("Registration Successfully Done ");
            String e,d,p,q,el_p,y1,el_prime;
            rsa r = new rsa();
            r.calculateParameter();
            e = r.getEF();
            d = r.getD();
            p = r.getP();
            q = r.getQ();

            //For  EL gamal

            ELGamal gl = new ELGamal();
            gl.calculate();
            el_p = gl.getEL_p();
            y1=gl.get_y1();
            el_prime = gl.get_prim();

            //for Ciper key
            Random ran = new Random();
            int random = ran.nextInt(60);
            String key = String.valueOf(random);

            database db = new database();
            db.registration(reg_name.getText(),reg_password.getText(),e,d,p,q,key,el_p,y1,el_prime);
        }
        else if (event.getSource() == rsa_en_button){
            rsa r = new rsa();
            try {
                r.encrypt(filePath,user);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (event.getSource() == rsa_de_button){
            rsa r = new rsa();
            try {
                r.decrypt(filePath,user);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        else if (event.getSource() == Ciper_en_button){
            cisher c = new cisher();
            try {
                c.ciper_encrypt(filePath,user);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (event.getSource() == Ciper_de_button){
            cisher c = new cisher();
            try {
                c.ciper_decryption(filePath,user);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else if (event.getSource() ==   EL_en_button){
            ELGamal el = new ELGamal();
            try {
                el.encrypt(filePath,user);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else if(event.getSource()== rsa_back_button){
            window.setScene(start_scr);
        }
        else if(event.getSource()== login_back_button){
            window.setScene(start_scr);
        }
        else if(event.getSource()== final_back_button){
            window.setScene(start_scr);
        }


    }
}
