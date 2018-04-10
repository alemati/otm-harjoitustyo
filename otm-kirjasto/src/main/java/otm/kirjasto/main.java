/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otm.kirjasto;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;

public class main extends Application {

    private static UserDao userDao;
    private static BookDao bookDao;

    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        userDao = new UserDao("tietokanta.db");
        bookDao = new BookDao("tietokanta.db");
        mainStage = primaryStage;
        Scene scene = createLoginPage();

        File f = new File("montana.css");
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

        mainStage.setScene(scene);

        mainStage.setOnCloseRequest(e -> primaryStage.close());

        mainStage.show();
    }

    public static Scene createLoginPage() {

        Label tekstiAlla = new Label("");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(8);

        Label kayttajatunnus = new Label("Username: ");
        GridPane.setConstraints(kayttajatunnus, 0, 0);
        Label salasana = new Label("Password: ");
        GridPane.setConstraints(salasana, 0, 1);

        TextField ktSyot = new TextField();
        ktSyot.setPromptText("username");
        GridPane.setConstraints(ktSyot, 1, 0);
        TextField ssSyot = new TextField();
        ssSyot.setPromptText("password");
        GridPane.setConstraints(ssSyot, 1, 1);

        Button but1 = new Button("Loggin");
        but1.setOnAction(e -> {
            String name = ktSyot.getText();
            String pass = ssSyot.getText();
            User user = new User(name, pass);
            User onko = null;
            try {
                onko = userDao.findOne(name);
            } catch (SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (onko != null && pass.equals(onko.getPass()) && name.equals("admin")) {
                mainStage.setScene(createAdminPage(user));
            } else if (onko != null && pass.equals(onko.getPass()) && !name.equals("admin")) {
                mainStage.setScene(createUserPage(user));
            }
            tekstiAlla.setText("Nickname and/or password invalid(s).");
            ktSyot.clear();
            ssSyot.clear();
        });

        GridPane.setConstraints(but1, 1, 2);

        Button but2 = new Button("Create an account");
        but2.setOnAction(e -> {
            mainStage.setScene(createAccountRegistrationPage());
        });
        Button but3 = new Button("Show admin account info");
        but3.setOnAction(e -> {
            AlertBox.display("Admin account", "Username: admin\nPassword: 1234");
        });
        GridPane.setConstraints(but3, 1, 4);
        GridPane.setConstraints(but2, 1, 3);
        GridPane.setConstraints(tekstiAlla, 1, 5);
        gridPane.getChildren().addAll(kayttajatunnus, salasana, ktSyot, ssSyot, but1, but2, but3, tekstiAlla);
        Scene scene = new Scene(gridPane, 600, 400);
        scene.getStylesheets().add("montana.css");
//        scene.getStylesheets().add(main.class.getResource("montana.css").toExternalForm());
        return scene;
    }

    public static Scene createAccountRegistrationPage() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(8);
        Label error = new Label("");
        Label kayttajatunnus = new Label("Username   ");
        GridPane.setConstraints(kayttajatunnus, 0, 0);
        Label salasana = new Label("Password    ");
        GridPane.setConstraints(salasana, 0, 1);

        TextField ktSyot = new TextField();
        ktSyot.setPromptText("username");
        GridPane.setConstraints(ktSyot, 1, 0);
        TextField ssSyot = new TextField();
        ssSyot.setPromptText("password");
        GridPane.setConstraints(ssSyot, 1, 1);

        Button but1 = new Button("Create account");
        but1.setOnAction(e -> {
            try {
                String name = ktSyot.getText();
                String pass = ssSyot.getText();
                User us = new User(name, pass);
                if (name.length() > 3 && pass.length() > 3 && userDao.findOne(name) == null) {
                    userDao.saveOrUpdate(us);
                    mainStage.setScene(createUserPage(us));
                }
                if (userDao.findOne(name) != null) {
                    error.setText("Username is already taken");
                }
                if (name.length() < 4) {
                    error.setText("Username is too short");
                }
                if (pass.length() < 4) {
                    error.setText("Password is too short");
                }
                ktSyot.clear();
                ssSyot.clear();
            } catch (SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        GridPane.setConstraints(but1, 1, 2);
        GridPane.setConstraints(error, 1, 4);

        Button but2 = new Button("Back to login page");
        but2.setOnAction(e -> {
            mainStage.setScene(createLoginPage());
        });
        GridPane.setConstraints(but2, 2, 2);
        GridPane.setConstraints(error, 1, 4);
        gridPane.getChildren().addAll(kayttajatunnus, salasana, ktSyot, ssSyot, but1, but2, error);
        Scene scene = new Scene(gridPane, 600, 400);

        return scene;
    }

    public static Scene createAdminPage(User user) {
        Scene scene = new Scene(new HBox());
        try {
            VBox layout = new VBox(10);
            Label welcome = new Label("Yoy are loggened in as ADMIN");
            Button but1 = new Button("Logout");
            but1.setOnAction(e -> {
                mainStage.setScene(createLoginPage());
            });

            ListView<User> lista = new ListView<>();
            userDao.findAll().forEach(u -> {
                if (!u.getName().equals("admin")) {
                    lista.getItems().add(u);
                }
            });

            ListView<Book> lista2 = new ListView<>();
            bookDao.findAll().forEach(k -> {
                lista2.getItems().add(k);
            });

            Button but2 = new Button("Delete selected user");

            but2.setOnAction(e -> {
                User selectedUser = lista.getSelectionModel().getSelectedItem();

                if (selectedUser != null) {
                    String ans = ConfirmBox.display("Are you sure?", "When you delete user, all \nbooks borrowed by him atm \nwill be left to him\n(i.e. books will be deleted too)");
                    if (ans.equals("Delete user")) {
                        try {
                            ArrayList<Book> bookLista = bookDao.findAllWhere(selectedUser);
                            bookLista.stream().forEach(k -> {
                                try {
                                    bookDao.delete(k.getId());
                                } catch (SQLException ex) {
                                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                            userDao.delete(selectedUser.getName());
                            mainStage.setScene(createAdminPage(user));
                        } catch (SQLException ex) {
                            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            });

            Button but3 = new Button("Delete selected book");

            but3.setOnAction(e -> {
                Book selectedBook = lista2.getSelectionModel().getSelectedItem();

                if (selectedBook != null && selectedBook.getOmistaja().equals("admin")) {
                    try {
                        bookDao.delete(selectedBook.getId());
                        mainStage.setScene(createAdminPage(user));
                    } catch (SQLException ex) {
                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    AlertBox.display("You can't delete this book!", "Book that you want \n to delete is borrowed!");
                }
            });

            Button but4 = new Button("Add book");

            but4.setOnAction(e -> {
                mainStage.setScene(createBookAdditionPage(user));
            });
            HBox hbox = new HBox(3);
            HBox hboxButtons = new HBox(3);
            hboxButtons.getChildren().addAll(but1, but2, but3, but4);
            Label users = new Label("List of users (username, password):");
            Label listOfBooks = new Label("List of books:");
            VBox userBox = new VBox();
            VBox bookBox = new VBox();
            userBox.getChildren().addAll(users, lista);
            bookBox.getChildren().addAll(listOfBooks, lista2);
            hbox.getChildren().addAll(userBox, bookBox);
            layout.getChildren().addAll(welcome, hbox, hboxButtons);
            scene = new Scene(layout, 600, 400);
            return scene;
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return scene;
    }

    public static Scene createUserPage(User user) {
        VBox vBox = new VBox(10);
        HBox hBox = new HBox(8);
        Label welcome = new Label(user.getName() + ", welcome to your page!");
        Label yourBooks = new Label("Yours books:");
        Label availabel = new Label("Available books:");
        Button but1 = new Button("Logout");
        but1.setOnAction(e -> {
            mainStage.setScene(createLoginPage());
        });

        Button but2 = new Button("Delete this account");
        but2.setOnAction(e -> {
            try {
                userDao.delete(user.getName());
                mainStage.setScene(createLoginPage());
            } catch (SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        ListView<Book> lista1 = new ListView<>();
        try {
            bookDao.findAllVapaat().forEach(k -> {
                lista1.getItems().add(k);
            });
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

        ListView<Book> lista2 = new ListView<>();
        try {
            bookDao.findAllWhere(user).forEach(k -> {
                lista2.getItems().add(k);
            });
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Button but3 = new Button("Borrow choosen book");
        but3.setOnAction(e -> {
            try {
                Book valittuKirja = lista1.getSelectionModel().getSelectedItem();
                bookDao.vaihdaOmistaja(valittuKirja, user);
                mainStage.setScene(createUserPage(user));
            } catch (SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        Button but4 = new Button("Return choosen book");
        but4.setOnAction(e -> {
            try {
                Book valittuKirja = lista2.getSelectionModel().getSelectedItem();
                bookDao.palauttaaKirja(valittuKirja);
                mainStage.setScene(createUserPage(user));
            } catch (SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        VBox buttons = new VBox(8);
        buttons.getChildren().addAll(but1, but2, but3, but4);
        VBox vBoxOmatKirjat = new VBox(5);
        VBox vBoxVapaatKirjat = new VBox(5);
        vBoxOmatKirjat.getChildren().addAll(yourBooks, lista2, but4);
        vBoxVapaatKirjat.getChildren().addAll(availabel, lista1, but3);
        hBox.getChildren().addAll(vBoxOmatKirjat, vBoxVapaatKirjat);
        vBox.getChildren().addAll(welcome, hBox, but1, but2);
        Scene scene = new Scene(vBox, 600, 400);
        return scene;
    }

    public static Scene createBookAdditionPage(User user) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(8);
        Label error = new Label("");
        Label nimi = new Label("Name of new book? ");
        GridPane.setConstraints(nimi, 0, 0);
        Label kirj = new Label("Who wrote it? ");
        GridPane.setConstraints(kirj, 0, 1);
        Label vuosi = new Label("What is publishing year? ");
        GridPane.setConstraints(vuosi, 0, 2);

        TextField nimiSyot = new TextField();
        nimiSyot.setPromptText("name");
        GridPane.setConstraints(nimiSyot, 1, 0);
        TextField kirjSyot = new TextField();
        kirjSyot.setPromptText("writer");
        GridPane.setConstraints(kirjSyot, 1, 1);
        TextField vuosiSyot = new TextField();
        vuosiSyot.setPromptText("year");
        GridPane.setConstraints(vuosiSyot, 1, 2);

        Button but1 = new Button("Add book");
        but1.setOnAction(e -> {
            try {
                if (!nimiSyot.getText().isEmpty() && !kirjSyot.getText().isEmpty()) {
                    Book uusiKirja = new Book(bookDao.viimeisenKirjanId() + 1, nimiSyot.getText(), kirjSyot.getText(), Integer.parseInt(vuosiSyot.getText()));
                    bookDao.saveOrUpdate(uusiKirja);
                    mainStage.setScene(createAdminPage(user));

                } else {
                    error.setText("Upps! Try again.");
                }

            } catch (SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Button but2 = new Button("Back");
        but2.setOnAction(e -> {
            mainStage.setScene(createAdminPage(user));
        });
        GridPane.setConstraints(but1, 1, 3);
        GridPane.setConstraints(error, 1, 4);
        GridPane.setConstraints(but2, 1, 5);
        gridPane.getChildren().addAll(nimi, kirj, vuosi, nimiSyot, kirjSyot, vuosiSyot, but1, but2, error);
        Scene scene = new Scene(gridPane, 600, 400);
        return scene;
    }
}
