module minesweeper {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens minesweeper to javafx.fxml;
    exports minesweeper;
}
