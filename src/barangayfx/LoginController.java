package barangayfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class LoginController {

    // ── Hardcoded admin credentials ───────────────────────────────────────
    public  static final String ADMIN_USER = "admin";
    public  static       String ADMIN_PASS = "barangay2026";

    // ── FXML fields ───────────────────────────────────────────────────────
    @FXML private TextField     txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private TextField     txtPasswordVisible;
    @FXML private CheckBox      chkShowPassword;
    @FXML private Label         lblError;

    private int failedAttempts = 0;

    // ── Show / hide password ──────────────────────────────────────────────
    @FXML
    private void togglePassword() {
        if (chkShowPassword.isSelected()) {
            txtPasswordVisible.setText(txtPassword.getText());
            txtPasswordVisible.setVisible(true);
            txtPasswordVisible.setManaged(true);
            txtPassword.setVisible(false);
            txtPassword.setManaged(false);
        } else {
            txtPassword.setText(txtPasswordVisible.getText());
            txtPassword.setVisible(true);
            txtPassword.setManaged(true);
            txtPasswordVisible.setVisible(false);
            txtPasswordVisible.setManaged(false);
        }
    }

    // ── Login handler ─────────────────────────────────────────────────────
    @FXML
    private void handleLogin() {
        if (failedAttempts >= 5) {
            showError("Account locked. Contact your Barangay IT Officer.");
            return;
        }

        String user = txtUsername.getText().trim();
        String pass = chkShowPassword.isSelected()
                      ? txtPasswordVisible.getText()
                      : txtPassword.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            showError("Please enter your username and password.");
            return;
        }

        if (ADMIN_USER.equals(user) && ADMIN_PASS.equals(pass)) {
            openMainApp();
        } else {
            failedAttempts++;
            int left = 5 - failedAttempts;
            if (left > 0) {
                showError("Incorrect credentials. " + left + " attempt(s) remaining.");
            } else {
                showError("Account locked. Contact your Barangay IT Officer.");
            }
            txtPassword.clear();
            txtPasswordVisible.clear();
        }
    }

    // ── Open main app window ──────────────────────────────────────────────
    private void openMainApp() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("barangayfx.fxml"));
            Parent root = loader.load();

            Controller controller = loader.getController();
            controller.setCurrentUser(ADMIN_USER);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                getClass().getResource("style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Barangay 143 – Resident & Incident Reporting System");
            stage.setScene(scene);
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            stage.setMaximized(true);  // ADD THIS LINE
            stage.show();

            // Close login window
            Stage loginStage = (Stage) txtUsername.getScene().getWindow();
            loginStage.close();

        } catch (Exception e) {
            showError("Failed to load application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ── Helper ────────────────────────────────────────────────────────────
    private void showError(String msg) {
        lblError.setText(msg);
        lblError.setVisible(true);
        lblError.setManaged(true);
    }
}