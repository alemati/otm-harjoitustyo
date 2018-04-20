package ui;

import domain.User;
import domain.ConfirmBox;
import domain.Book;
import dao.BookDao;
import dao.UserDao;
import domain.AlertBox;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
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
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
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
        userDao = new UserDao("database.db");
        bookDao = new BookDao("database.db");
        mainStage = primaryStage;
        Scene scene = createLoginPage();
        mainStage.setScene(scene);
        mainStage.setOnCloseRequest(e -> primaryStage.close());
        mainStage.show();
    }

    public static Scene createLoginPage() {
        Label errorMessage = new Label("");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(8);
        BorderPane borderpane = new BorderPane();

        Label username = new Label("Username: ");
        GridPane.setConstraints(username, 0, 0);
        Label password = new Label("Password: ");
        GridPane.setConstraints(password, 0, 1);

        TextField unInput = new TextField();
        unInput.setPromptText("username");
        GridPane.setConstraints(unInput, 1, 0);
        TextField pwInput = new TextField();
        pwInput.setPromptText("password");
        GridPane.setConstraints(pwInput, 1, 1);

        Button logginButton = new Button("Loggin");
        logginButton.setOnAction(e -> {
            String name = unInput.getText();
            String pass = pwInput.getText();
            User user = new User(name, pass);
            User onko = null;
            try {
                onko = userDao.findByUsername(name);
            } catch (SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (onko != null && pass.equals(onko.getPass()) && name.equals("admin")) {
                mainStage.setScene(createAdminPage(user));
            } else if (onko != null && pass.equals(onko.getPass()) && !name.equals("admin")) {
                mainStage.setScene(createUserPage(user));
            }
            errorMessage.setText("Username and/or password invalid(s).");
            unInput.clear();
            pwInput.clear();
        });

        GridPane.setConstraints(logginButton, 1, 2);

        Button newAccButton = new Button("Create an account");
        newAccButton.setOnAction(e -> {
            mainStage.setScene(createAccountRegistrationPage());
        });
        Button but3 = new Button("Show admin account info");
        but3.setOnAction(e -> {
            AlertBox.display("Admin account", "Username: admin\nPassword: 1234");
        });
        GridPane.setConstraints(but3, 1, 4);
        GridPane.setConstraints(newAccButton, 1, 3);
        GridPane.setConstraints(errorMessage, 1, 5);
        gridPane.getChildren().addAll(username, password, unInput, pwInput, logginButton, newAccButton, but3, errorMessage);
        borderpane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(borderpane, 600, 400);
        return scene;
    }

    public static Scene createAccountRegistrationPage() {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(8);
        Label error = new Label("");
        Label username = new Label("Username   ");
        GridPane.setConstraints(username, 0, 0);
        Label password = new Label("Password    ");
        GridPane.setConstraints(password, 0, 1);

        TextField unInput = new TextField();
        unInput.setPromptText("username");
        GridPane.setConstraints(unInput, 1, 0);
        TextField pwInput = new TextField();
        pwInput.setPromptText("password");
        GridPane.setConstraints(pwInput, 1, 1);

        Button createAccButton = new Button("Create account");
        createAccButton.setOnAction(e -> {
            try {
                String name = unInput.getText();
                String pass = pwInput.getText();
                User us = new User(name, pass);
                if (name.length() > 3 && pass.length() > 3 && userDao.findByUsername(name) == null) {
                    userDao.save(us);
                    mainStage.setScene(createUserPage(us));
                }
                if (userDao.findByUsername(name) != null) {
                    error.setText("Username is already taken");
                }
                if (name.length() < 4) {
                    error.setText("Username is too short");
                }
                if (pass.length() < 4) {
                    error.setText("Password is too short");
                }
                unInput.clear();
                pwInput.clear();
            } catch (SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        GridPane.setConstraints(createAccButton, 1, 2);
        GridPane.setConstraints(error, 1, 4);

        Button backButton = new Button("Back to login page");
        backButton.setOnAction(e -> {
            mainStage.setScene(createLoginPage());
        });
        GridPane.setConstraints(backButton, 1, 3);
        GridPane.setConstraints(error, 1, 4);
        gridPane.getChildren().addAll(username, password, unInput, pwInput, createAccButton, backButton, error);
        borderPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(borderPane, 600, 400);
        return scene;
    }

    public static Scene createAdminPage(User user) {
        Scene scene = new Scene(new HBox());
        try {
            VBox mainVBox = new VBox(10);
            
            Button logoutButton = new Button("Logout");
            logoutButton.setOnAction(e -> {
                mainStage.setScene(createLoginPage());
            });

            ListView<User> userList = new ListView<>();
            userDao.findAll().forEach(u -> {
                if (!u.getName().equals("admin")) {
                    userList.getItems().add(u);
                }
            });

            ListView<Book> bookList = new ListView<>();
            bookDao.findAllFree().forEach(k -> {
                bookList.getItems().add(k);
            });
            
            Button showUserButton = new Button("Show profile of selected user");
            showUserButton.setOnAction(e -> {
                User selectedUser = userList.getSelectionModel().getSelectedItem();
                mainStage.setScene(createAdminUserPage(selectedUser));
            });

            Button deleteBookButton = new Button("Delete selected book");
            deleteBookButton.setOnAction(e -> {
                Book selectedBook = bookList.getSelectionModel().getSelectedItem();
                if (selectedBook != null && selectedBook.getOmistaja().equals("admin")) {
                    try {
                        bookDao.delete(selectedBook.getId());
                        mainStage.setScene(createAdminPage(user));
                    } catch (SQLException ex) {
                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    AlertBox.display("You can't delete this book!", 
                            "Book that you want\nto delete is borrowed!");
                }
            });

            Button addBookButton = new Button("Add book");
            addBookButton.setOnAction(e -> {
                mainStage.setScene(createBookAdditionPage(user));
            });
            
            Button allBorrowedBooksButton = new Button("Show all borrowed books");
            allBorrowedBooksButton.setOnAction(e -> {
                mainStage.setScene(createBorrowedBooksPage());
            });
            
            HBox firstRow = new HBox();
            HBox secondRow = new HBox(3);
            HBox thirdRow = new HBox(3);
            
            Label welcomeText = new Label("Yoy are logged in as ADMIN         ");
            Label usersText = new Label("List of users (username, password):");
            Label booksText = new Label("List of currently free books:    ");
            
            HBox aboveBookList = new HBox();
            
            VBox userBox = new VBox(3);
            VBox bookBox = new VBox(3);
            aboveBookList.getChildren().addAll(booksText,addBookButton);
            userBox.getChildren().addAll(usersText, userList,showUserButton);
            bookBox.getChildren().addAll(aboveBookList, bookList, deleteBookButton,addBookButton);
            
            firstRow.getChildren().addAll(welcomeText,logoutButton);
            secondRow.getChildren().addAll(userBox, bookBox);
            thirdRow.getChildren().addAll(allBorrowedBooksButton);
            
            mainVBox.getChildren().addAll(firstRow, secondRow, thirdRow);
            
            scene = new Scene(mainVBox, 600, 400);
            return scene;
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scene;
    }

    public static Scene createUserPage(User user) {
        VBox mainVBox = new VBox(10);
        HBox secondRow = new HBox(8);
        
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            mainStage.setScene(createLoginPage());
        });

        ListView<Book> lista1 = new ListView<>();
        try {
            bookDao.findAllFree().forEach(k -> {
                lista1.getItems().add(k);
            });
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

        ListView<Book> lista2 = new ListView<>();
        try {
            bookDao.findAllWhichBelongsTo(user).forEach(k -> {
                lista2.getItems().add(k);
            });
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Button borrowButton = new Button("Borrow choosen book");
        borrowButton.setOnAction(e -> {
            try {
                Book valittuKirja = lista1.getSelectionModel().getSelectedItem();
                bookDao.changeOwner(valittuKirja, user);
                mainStage.setScene(createUserPage(user));
            } catch (SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        Button returnButton = new Button("Return choosen book");
        returnButton.setOnAction(e -> {
            try {
                Book valittuKirja = lista2.getSelectionModel().getSelectedItem();
                bookDao.returnBook(valittuKirja);
                mainStage.setScene(createUserPage(user));
            } catch (SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        Label welcome = new Label(user.getName() + ", welcome to your page!");
        Label yourBooks = new Label("Yours books:");
        Label availabel = new Label("Available books:");
        
        VBox vBoxOwnBooks = new VBox(5);
        VBox vBoxAvailableBooks = new VBox(5);
        
        vBoxOwnBooks.getChildren().addAll(yourBooks, lista2, returnButton);
        vBoxAvailableBooks.getChildren().addAll(availabel, lista1, borrowButton);
        
        secondRow.getChildren().addAll(vBoxOwnBooks, vBoxAvailableBooks);
        mainVBox.getChildren().addAll(welcome, secondRow, logoutButton);
        Scene scene = new Scene(mainVBox, 600, 400);
        return scene;
    }

    public static Scene createBookAdditionPage(User user) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(8);
        Label error = new Label("");
        Label name = new Label("Name of new book? ");
        GridPane.setConstraints(name, 0, 0);
        Label writer = new Label("Who wrote it? ");
        GridPane.setConstraints(writer, 0, 1);
        Label year = new Label("What is publishing year? ");
        GridPane.setConstraints(year, 0, 2);

        TextField nmInput = new TextField();
        nmInput.setPromptText("name");
        GridPane.setConstraints(nmInput, 1, 0);
        TextField writerInput = new TextField();
        writerInput.setPromptText("writer");
        GridPane.setConstraints(writerInput, 1, 1);
        TextField yearInput = new TextField();
        yearInput.setPromptText("year");
        GridPane.setConstraints(yearInput, 1, 2);

        Button addBookButton = new Button("Add book");
        addBookButton.setOnAction(e -> {
            try {
                if (!nmInput.getText().isEmpty() && !writerInput.getText().isEmpty()) {
                    Book uusiKirja = new Book(bookDao.idOfLastBook() + 1, nmInput.getText(), writerInput.getText(), Integer.parseInt(yearInput.getText()));
                    bookDao.save(uusiKirja);
                    mainStage.setScene(createAdminPage(user));

                } else {
                    error.setText("Upps! Try again.");
                }

            } catch (SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            mainStage.setScene(createAdminPage(user));
        });
        GridPane.setConstraints(addBookButton, 1, 3);
        GridPane.setConstraints(error, 1, 5);
        GridPane.setConstraints(backButton, 1, 4);
        gridPane.getChildren().addAll(name, writer, year, nmInput, writerInput, yearInput, addBookButton, backButton, error);
        Scene scene = new Scene(gridPane, 600, 400);
        return scene;
    }

    public static Scene createAdminUserPage(User user) {
        Label userInfo = new Label("Username: " + user.getName() + ", password: " + user.getPass());
        Label userBooks = new Label("Books borrowed by " + user.getName() + " at this moment:");
        VBox layout = new VBox(4);
        ListView<Book> bookList = new ListView<>();
        try {
            bookDao.findAll().forEach(k -> {
                if (k.getOmistaja().equals(user.getName())) {
                    bookList.getItems().add(k);
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Button deleteButton = new Button("Delete this account");
        deleteButton.setOnAction(e -> {
            try {
                String ans = ConfirmBox.display("Are you sure?", "When you delete user, all \n"
                        + "books borrowed by him atm \n"
                        + "will be left to him\n(i.e. books will be deleted too)");
                if (ans.equals("Delete user")) {
                    ArrayList<Book> bookLista = bookDao.findAllWhichBelongsTo(user);
                    bookLista.stream().forEach(k -> {
                        try {
                            bookDao.delete(k.getId());
                        } catch (SQLException ex) {
                            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    userDao.delete(user.getName());
                    mainStage.setScene(createAdminPage(user));
                }

            } catch (SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            mainStage.setScene(createAdminPage(user));
        });
        layout.getChildren().addAll(userInfo, userBooks, bookList, deleteButton, backButton);
        Scene scene = new Scene(layout, 600, 400);
        return scene;
    }
    
    public static Scene createBorrowedBooksPage() {
        Label text = new Label("List of all borrowed books");
        VBox layout = new VBox(4);
        List list = new ArrayList();
        ListView<String> bookList = new ListView<>();
        try {
            list = bookDao.findAllBorrowed();
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        list.stream().forEach(k -> {
            Book book = (Book) k;
            bookList.getItems().add(k.toString() + ", borrowed by " + book.getOmistaja());
        });
        
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            mainStage.setScene(createAdminPage(new User("d", "v")));
        });
        layout.getChildren().addAll(text,bookList,backButton);
        Scene scene = new Scene(layout, 600, 400);
        return scene;
    }

}
