package barangayfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class Controller {

    // ── Topbar ────────────────────────────────────────────────────────────
    @FXML private Label lblCurrentUser;
    @FXML private Label lblCurrentDate;

    // ── Navigation ────────────────────────────────────────────────────────
    @FXML private Button btnNavDashboard;
    @FXML private Button btnNavResidents;
    @FXML private Button btnNavIncidents;
    @FXML private Button btnNavInventory;
    @FXML private Button btnNavSettings;

    // ── Views ─────────────────────────────────────────────────────────────
    @FXML private VBox viewDashboard;
    @FXML private VBox viewResidents;
    @FXML private VBox viewIncidents;
    @FXML private VBox viewInventory;
    @FXML private VBox viewSettings;

    // ── Dashboard metrics ─────────────────────────────────────────────────
    @FXML private Label metricResidents;
    @FXML private Label metricResidentsSub;
    @FXML private Label metricOpenIncidents;
    @FXML private Label metricOpenIncidentsSub;
    @FXML private Label metricResolved;
    @FXML private Label metricResolvedSub;
    @FXML private Label metricPending;
    @FXML private Label metricPendingSub;

    // ── Dashboard table ───────────────────────────────────────────────────
    @FXML private TableView<Resident>           dashRecentTable;
    @FXML private TableColumn<Resident, String> dashColAvatar;
    @FXML private TableColumn<Resident, String> dashColName;
    @FXML private TableColumn<Resident, String> dashColAddress;
    @FXML private TableColumn<Resident, String> dashColDate;
    @FXML private TableColumn<Resident, String> dashColStatus;

    // ── Incident Overview (donut) ─────────────────────────────────────────
    @FXML private Canvas incidentDonut;
    @FXML private Label  donutTotal;
    @FXML private Label  legendOpen;
    @FXML private Label  legendPending;
    @FXML private Label  legendResolved;

    // ── Purok Summary ─────────────────────────────────────────────────────
    @FXML private VBox purokList;

    // ── Residents ─────────────────────────────────────────────────────────
    @FXML private Label                         lblResidentCount;
    @FXML private TextField                     txtSearch;
    @FXML private TableView<Resident>           tableResidents;
    @FXML private TableColumn<Resident, String> colName;
    @FXML private TableColumn<Resident, String> colAddress;
    @FXML private TableColumn<Resident, String> colBirthdate;
    @FXML private TableColumn<Resident, String> colContact;
    @FXML private TableColumn<Resident, String> colStatus;
    @FXML private TableColumn<Resident, Void>   colActions;

    // ── Incidents ─────────────────────────────────────────────────────────
    @FXML private Label                         lblIncidentCount;
    @FXML private TableView<Incident>           tableIncidents;
    @FXML private TableColumn<Incident, String> iColDesc;
    @FXML private TableColumn<Incident, String> iColLocation;
    @FXML private TableColumn<Incident, String> iColReporter;
    @FXML private TableColumn<Incident, String> iColStatus;
    @FXML private TableColumn<Incident, Void>   iColActions;

    // ── Inventory ─────────────────────────────────────────────────────────
    @FXML private Label                               lblInventoryCount;
    @FXML private Label                               lblInStock;
    @FXML private Label                               lblLowStock;
    @FXML private Label                               lblOutOfStock;
    @FXML private TextField                           txtInventorySearch;
    @FXML private TableView<InventoryItem>            tableInventory;
    @FXML private TableColumn<InventoryItem, String>  invColName;
    @FXML private TableColumn<InventoryItem, Integer> invColQty;
    @FXML private TableColumn<InventoryItem, String>  invColStatus;
    @FXML private TableColumn<InventoryItem, String>  invColLocation;
    @FXML private TableColumn<InventoryItem, String>  invColUpdated;
    @FXML private TableColumn<InventoryItem, Void>    invColActions;

    // ── Settings ──────────────────────────────────────────────────────────
    @FXML private PasswordField txtCurrentPass;
    @FXML private PasswordField txtNewPass;
    @FXML private PasswordField txtConfirmPass;
    @FXML private Label         lblSettingsMsg;

    // ── Data ──────────────────────────────────────────────────────────────
    private final ObservableList<Resident>      residentData   = FXCollections.observableArrayList();
    private final ObservableList<Resident>      dashRecentData = FXCollections.observableArrayList();
    private final ObservableList<Incident>      incidentData   = FXCollections.observableArrayList();
    private final ObservableList<InventoryItem> inventoryData  = FXCollections.observableArrayList();
    private FilteredList<Resident>      filteredResidents;
    private FilteredList<InventoryItem> filteredInventory;

    // ════════════════════════════════════════════════════════════════════════
    //  INIT
    // ════════════════════════════════════════════════════════════════════════
    public void initialize() {
        setupResidentTable();
        setupIncidentTable();
        setupDashboardTable();
        setupInventoryTable();
        loadAll();
        setActiveNav(btnNavDashboard);
        if (lblCurrentDate != null)
            lblCurrentDate.setText(
                LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
    }

    public void setCurrentUser(String username) {
        String display = username.substring(0, 1).toUpperCase() + username.substring(1);
        lblCurrentUser.setText(display);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  NAVIGATION
    // ════════════════════════════════════════════════════════════════════════
    @FXML private void showDashboard() {
        setView(viewDashboard); setActiveNav(btnNavDashboard); refreshDashboardMetrics();
    }
    @FXML private void showResidents() {
        setView(viewResidents); setActiveNav(btnNavResidents);
    }
    @FXML private void showIncidents() {
        setView(viewIncidents); setActiveNav(btnNavIncidents);
    }
    @FXML private void showInventory() {
        setView(viewInventory); setActiveNav(btnNavInventory); loadInventory();
    }
    @FXML private void showSettings() {
        setView(viewSettings); setActiveNav(btnNavSettings);
        txtCurrentPass.clear(); txtNewPass.clear(); txtConfirmPass.clear();
        lblSettingsMsg.setVisible(false); lblSettingsMsg.setManaged(false);
    }

    private void setView(VBox target) {
        for (VBox v : new VBox[]{viewDashboard, viewResidents,
                                  viewIncidents, viewInventory, viewSettings}) {
            v.setVisible(false); v.setManaged(false);
        }
        target.setVisible(true); target.setManaged(true);
    }

    private void setActiveNav(Button active) {
        for (Button b : new Button[]{btnNavDashboard, btnNavResidents,
                                      btnNavIncidents, btnNavInventory, btnNavSettings})
            b.getStyleClass().remove("nav-item-active");
        if (!active.getStyleClass().contains("nav-item-active"))
            active.getStyleClass().add("nav-item-active");
    }

    // ════════════════════════════════════════════════════════════════════════
    //  LOGOUT
    // ════════════════════════════════════════════════════════════════════════
    @FXML private void handleLogout() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to log out?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null); confirm.setTitle("Log out");
        if (confirm.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) return;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            javafx.geometry.Rectangle2D screen =
                javafx.stage.Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Stage loginStage = new Stage();
            loginStage.setTitle("Barangay 143 – Login");
            loginStage.setResizable(true);
            loginStage.setMaximized(true);
            loginStage.setScene(scene);
            loginStage.show();
            ((Stage) btnNavDashboard.getScene().getWindow()).close();
        } catch (Exception e) { showError("Failed to return to login: " + e.getMessage()); }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  TABLE SETUP
    // ════════════════════════════════════════════════════════════════════════
    private void setupResidentTable() {
        colName     .setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colAddress  .setCellValueFactory(new PropertyValueFactory<>("address"));
        colBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        colContact  .setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colStatus   .setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus   .setCellFactory(col -> new StatusBadgeCell<>());
        colActions  .setCellFactory(col -> new ActionMenuCell<>(
            this::openEditResidentModal, this::deleteResident));
        filteredResidents = new FilteredList<>(residentData, p -> true);
        tableResidents.setItems(filteredResidents);
        tableResidents.setPlaceholder(new Label("No residents found."));
        tableResidents.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableResidents.setRowFactory(tv -> {
            TableRow<Resident> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 1 && !row.isEmpty())
                    showResidentProfile(row.getItem());
            });
            return row;
        });
    }

    private void setupIncidentTable() {
        iColDesc    .setCellValueFactory(new PropertyValueFactory<>("description"));
        iColLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        iColReporter.setCellValueFactory(new PropertyValueFactory<>("reportedBy"));
        iColStatus  .setCellValueFactory(new PropertyValueFactory<>("status"));
        iColStatus  .setCellFactory(col -> new StatusBadgeCell<>());
        iColActions .setCellFactory(col -> new ActionMenuCell<>(
            this::openEditIncidentModal, this::deleteIncident));
        tableIncidents.setItems(incidentData);
        tableIncidents.setPlaceholder(new Label("No incidents reported."));
        tableIncidents.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupDashboardTable() {
        dashColAvatar.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dashColAvatar.setCellFactory(col -> new TableCell<Resident, String>() {
            @Override protected void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty || name == null) { setGraphic(null); return; }
                Label lbl = new Label(getInitials(name));
                lbl.getStyleClass().add("avatar-circle");
                lbl.setStyle("-fx-background-color:" + getAvatarColor(name) + ";");
                setGraphic(lbl);
            }
        });
        dashColName   .setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dashColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        dashColDate   .setCellValueFactory(new PropertyValueFactory<>("dateRegistered"));
        dashColStatus .setCellValueFactory(new PropertyValueFactory<>("status"));
        dashColStatus .setCellFactory(col -> new StatusBadgeCell<>());
        dashRecentTable.setItems(dashRecentData);
        dashRecentTable.setPlaceholder(new Label("No recent registrations."));
        dashRecentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupInventoryTable() {
        invColName    .setCellValueFactory(new PropertyValueFactory<>("itemName"));
        invColQty     .setCellValueFactory(new PropertyValueFactory<>("quantity"));
        invColStatus  .setCellValueFactory(new PropertyValueFactory<>("stockStatus"));
        invColLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        invColUpdated .setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        invColStatus  .setCellFactory(col -> new InventoryStatusCell());
        invColActions .setCellFactory(col -> new ActionMenuCell<>(
            this::openEditInventoryModal, this::deleteInventoryItem));
        filteredInventory = new FilteredList<>(inventoryData, p -> true);
        tableInventory.setItems(filteredInventory);
        tableInventory.setPlaceholder(new Label("No inventory items found."));
        tableInventory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  LOAD DATA
    // ════════════════════════════════════════════════════════════════════════
    private void loadAll() {
        loadResidents(); loadIncidents(); loadInventory(); refreshDashboardMetrics();
    }

    private void loadResidents() {
        try { ResidentService.loadAll(residentData); }
        catch (SQLException e) { showError("Failed to load residents: " + e.getMessage()); }
        lblResidentCount.setText(residentData.size() + " registered residents");
    }

    private void loadIncidents() {
        try { IncidentService.loadAll(incidentData); }
        catch (SQLException e) { showError("Failed to load incidents: " + e.getMessage()); }
        long open = incidentData.stream()
            .filter(i -> "Open".equalsIgnoreCase(i.getStatus())).count();
        lblIncidentCount.setText(open + " open · " + (incidentData.size() - open) + " resolved");
    }

    private void loadInventory() {
        try { InventoryService.loadAll(inventoryData); }
        catch (SQLException e) { showError("Failed to load inventory: " + e.getMessage()); }
        refreshInventoryMetrics();
    }

    private void refreshInventoryMetrics() {
        long inStock   = inventoryData.stream().filter(i -> "In Stock"    .equalsIgnoreCase(i.getStockStatus())).count();
        long lowStock  = inventoryData.stream().filter(i -> "Low Stock"   .equalsIgnoreCase(i.getStockStatus())).count();
        long outStock  = inventoryData.stream().filter(i -> "Out of Stock".equalsIgnoreCase(i.getStockStatus())).count();
        long restocked = inventoryData.stream().filter(i -> "Restocked"   .equalsIgnoreCase(i.getStockStatus())).count();
        lblInventoryCount.setText(inventoryData.size() + " total items");
        lblInStock   .setText("In Stock: "     + inStock);
        lblLowStock  .setText("Low Stock: "    + lowStock);
        lblOutOfStock.setText("Out of Stock: " + outStock + "   Restocked: " + restocked);
    }

    private void refreshDashboardMetrics() {
        try { ResidentService.loadRecent(dashRecentData); }
        catch (SQLException e) { showError("Failed to load recent residents: " + e.getMessage()); }

        long open     = incidentData.stream().filter(i -> "Open"    .equalsIgnoreCase(i.getStatus())).count();
        long resolved = incidentData.stream().filter(i -> "Resolved".equalsIgnoreCase(i.getStatus())).count();
        long pending  = incidentData.stream().filter(i -> "Pending" .equalsIgnoreCase(i.getStatus())).count();

        metricResidents    .setText(String.valueOf(residentData.size()));
        metricOpenIncidents.setText(String.valueOf(open));
        metricResolved     .setText(String.valueOf(resolved));
        metricPending      .setText(String.valueOf(pending));

        try {
            int thisMonth = ResidentService.countThisMonth();
            metricResidentsSub.setText("+" + thisMonth + " this month");
        } catch (Exception e) { metricResidentsSub.setText(""); }

        metricOpenIncidentsSub.setText(open     > 0 ? "Needs attention" : "All clear");
        metricResolvedSub     .setText(resolved > 0 ? "+1 this month"   : "");
        metricPendingSub      .setText(pending  > 0 ? "Needs attention" : "Up to date");

        if (legendOpen    != null) legendOpen    .setText(String.valueOf(open));
        if (legendPending != null) legendPending .setText(String.valueOf(pending));
        if (legendResolved!= null) legendResolved.setText(String.valueOf(resolved));
        if (donutTotal    != null) donutTotal    .setText(String.valueOf(open + pending + resolved));

        drawIncidentDonut(open, pending, resolved);
        loadPurokSummary();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  PUROK SUMMARY
    // ════════════════════════════════════════════════════════════════════════
    private void loadPurokSummary() {
        if (purokList == null) return;
        purokList.getChildren().clear();

        // Count residents per purok by scanning address field
        // Supports "Purok 1", "Purok1", and also catches "123 Mabini St, Purok 1" style
        long[] counts = new long[4]; // index 1=Purok1, 2=Purok2, 3=Purok3, 0=Other
        for (Resident r : residentData) {
            String addr = r.getAddress().toLowerCase();
            boolean matched = false;
            for (int p = 1; p <= 3; p++) {
                if (addr.contains("purok " + p) || addr.contains("purok" + p)) {
                    counts[p]++;
                    matched = true;
                    break;
                }
            }
            if (!matched) counts[0]++;
        }

        long total = residentData.size();

        // Define the 3 puroks + optional Other row
        String[]  labels = { "Purok 1", "Purok 2", "Purok 3", "Other" };
        long[]    values = { counts[1], counts[2], counts[3], counts[0] };
        String[]  colors = { "#4A90D9", "#7B68EE", "#27AE60", "#9a9aaa" };
        String[]  bgColors = { "#EFF6FF", "#F3F0FF", "#F0FDF4", "#F5F5F5" };

        for (int i = 0; i < labels.length; i++) {
            if (values[i] == 0) continue; // skip empty puroks

            String label   = labels[i];
            long   count   = values[i];
            String color   = colors[i];
            String bgColor = bgColors[i];
            double pct     = total > 0 ? (count * 100.0 / total) : 0;

            // ── Purok name label ──────────────────────────────────────────
            Label nameLbl = new Label(label);
            nameLbl.setStyle(
                "-fx-font-size:12px;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#1B1F3B;");
            HBox.setHgrow(nameLbl, Priority.ALWAYS);

            // ── Count badge ───────────────────────────────────────────────
            Label countBadge = new Label(count + " resident" + (count != 1 ? "s" : ""));
            countBadge.setStyle(
                "-fx-font-size:11px;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:" + color + ";" +
                "-fx-background-color:" + bgColor + ";" +
                "-fx-background-radius:10;" +
                "-fx-padding:2 8 2 8;");

            HBox nameRow = new HBox(nameLbl, countBadge);
            nameRow.setAlignment(Pos.CENTER_LEFT);

            // ── Percentage label ──────────────────────────────────────────
            Label pctLbl = new Label(String.format("%.1f%%", pct));
            pctLbl.setStyle(
                "-fx-font-size:10px;" +
                "-fx-text-fill:#9a9aaa;" +
                "-fx-min-width:36;" +
                "-fx-alignment:CENTER_RIGHT;");

            // ── Progress track ────────────────────────────────────────────
            StackPane track = new StackPane();
            track.setStyle(
                "-fx-background-color:#F0F2F5;" +
                "-fx-background-radius:4;");
            track.setMinHeight(7);
            track.setMaxHeight(7);
            track.setPrefHeight(7);
            track.setMaxWidth(Double.MAX_VALUE);

            Region fill = new Region();
            fill.setStyle(
                "-fx-background-color:" + color + ";" +
                "-fx-background-radius:4;");
            fill.setPrefHeight(7);
            fill.setMaxHeight(7);

            track.getChildren().add(fill);
            StackPane.setAlignment(fill, Pos.CENTER_LEFT);

            // Bind fill width to track width × percentage
            final double finalPct = pct;
            track.widthProperty().addListener((obs, o, n) ->
                fill.setPrefWidth(n.doubleValue() * finalPct / 100.0));

            HBox barRow = new HBox(6, track, pctLbl);
            barRow.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(track, Priority.ALWAYS);

            // ── Assemble row ──────────────────────────────────────────────
            VBox item = new VBox(4, nameRow, barRow);
            item.setPadding(new Insets(7, 2, 7, 2));
            item.setStyle(
                "-fx-border-color:transparent transparent #F0F2F5 transparent;" +
                "-fx-border-width:0 0 1 0;");

            purokList.getChildren().add(item);
        }

        // ── Footer: total line ────────────────────────────────────────────
        if (total > 0) {
            Region spacer = new Region();
            spacer.setPrefHeight(4);

            Label totalLbl = new Label("Total: " + total + " residents");
            totalLbl.setStyle(
                "-fx-font-size:11px;" +
                "-fx-text-fill:#7a7a8a;" +
                "-fx-font-weight:bold;");
            totalLbl.setPadding(new Insets(4, 2, 0, 2));

            purokList.getChildren().addAll(spacer, totalLbl);
        }

        if (residentData.isEmpty()) {
            Label empty = new Label("No resident data available.");
            empty.setStyle("-fx-font-size:12px;-fx-text-fill:#9a9aaa;");
            purokList.getChildren().add(empty);
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  DONUT CHART
    // ════════════════════════════════════════════════════════════════════════
    private void drawIncidentDonut(long open, long pending, long resolved) {
        if (incidentDonut == null) return;
        GraphicsContext gc = incidentDonut.getGraphicsContext2D();
        double w = incidentDonut.getWidth(), h = incidentDonut.getHeight();
        gc.clearRect(0, 0, w, h);
        double total  = open + pending + resolved;
        double cx     = w / 2, cy = h / 2;
        double outerR = Math.min(w, h) / 2 - 6;
        double innerR = outerR * 0.55;
        if (total == 0) {
            gc.setFill(Color.web("#E5E7EB"));
            gc.fillOval(cx - outerR, cy - outerR, outerR * 2, outerR * 2);
            gc.setFill(Color.WHITE);
            gc.fillOval(cx - innerR, cy - innerR, innerR * 2, innerR * 2);
            return;
        }
        double[] values = { open, pending, resolved };
        String[] colors = { "#EF4444", "#F97316", "#22C55E" };
        double startAngle = -90;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == 0) continue;
            double sweep = (values[i] / total) * 360.0;
            gc.setFill(Color.web(colors[i]));
            gc.fillArc(cx - outerR, cy - outerR, outerR * 2, outerR * 2,
                       startAngle, -sweep, ArcType.ROUND);
            startAngle -= sweep;
        }
        gc.setFill(Color.WHITE);
        gc.fillOval(cx - innerR, cy - innerR, innerR * 2, innerR * 2);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  SEARCH
    // ════════════════════════════════════════════════════════════════════════
    @FXML private void filterResidents() {
        String q = txtSearch.getText().toLowerCase().trim();
        filteredResidents.setPredicate(r -> q.isEmpty() ||
            r.getFullName() .toLowerCase().contains(q) ||
            r.getAddress()  .toLowerCase().contains(q) ||
            r.getContactNo().toLowerCase().contains(q) ||
            r.getStatus()   .toLowerCase().contains(q));
        lblResidentCount.setText(filteredResidents.size() + " residents shown");
    }

    @FXML private void filterInventory() {
        String q = txtInventorySearch.getText().toLowerCase().trim();
        filteredInventory.setPredicate(i -> q.isEmpty() ||
            i.getItemName()   .toLowerCase().contains(q) ||
            i.getStockStatus().toLowerCase().contains(q) ||
            i.getLocation()   .toLowerCase().contains(q));
        lblInventoryCount.setText(filteredInventory.size() + " items shown");
    }

    // ════════════════════════════════════════════════════════════════════════
    //  QUICK ACTIONS
    // ════════════════════════════════════════════════════════════════════════
    @FXML private void quickAddResident() { showResidentModal("Add New Resident", null); }
    @FXML private void quickAddIncident() { showIncidentModal("Report Incident",  null); }

    // ════════════════════════════════════════════════════════════════════════
    //  RESIDENT PROFILE MODAL
    // ════════════════════════════════════════════════════════════════════════
    private void showResidentProfile(Resident r) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UTILITY);
        modal.setTitle("Resident Profile — " + r.getFullName());
        modal.setResizable(false);

        String imgFile;
        String residentName = r.getFullName().trim().toLowerCase();
        if (residentName.equals("drey custodio")) {
            imgFile = "drey.jpg";
        } else if (residentName.equals("cristopher dabu")) {
            imgFile = "dabs.jpg";
        } else if (residentName.equals("kristopher edora")) {
            imgFile = "tope.jpg";
        } else {
            imgFile = "Female".equalsIgnoreCase(r.getGender()) ? "female.png" : "male.png";
        }
        ImageView avatar = new ImageView();
        try {
            java.net.URL imgUrl = getClass().getResource(imgFile);
            if (imgUrl != null) avatar.setImage(new Image(imgUrl.toExternalForm()));
        } catch (Exception ignored) {}
        avatar.setFitWidth(100);
        avatar.setFitHeight(100);
        avatar.setPreserveRatio(true);

        String qrText =
            "BARANGAY 143 - RESIDENT RECORD\n" +
            "Name: "              + r.getFullName()         + "\n" +
            "Gender: "            + r.getGender()           + "\n" +
            "Civil Status: "      + r.getCivilStatus()      + "\n" +
            "Birthdate: "         + r.getBirthdate()        + "\n" +
            "Address: "           + r.getAddress()          + "\n" +
            "Contact No.: "       + r.getContactNo()        + "\n" +
            "Email: "             + r.getEmail()            + "\n" +
            "Occupation: "        + r.getOccupation()       + "\n" +
            "Emergency Contact: " + r.getEmergencyContact() + "\n" +
            "Status: "            + r.getStatus()           + "\n" +
            "Barangay 143, Olongapo City";

        ImageView qrView = new ImageView();
        try {
            int qrSize = 300;
            java.util.Map<com.google.zxing.EncodeHintType, Object> hints =
                new java.util.HashMap<>();
            hints.put(com.google.zxing.EncodeHintType.ERROR_CORRECTION,
                com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.M);
            hints.put(com.google.zxing.EncodeHintType.MARGIN, 2);
            hints.put(com.google.zxing.EncodeHintType.CHARACTER_SET, "UTF-8");
            com.google.zxing.qrcode.QRCodeWriter writer =
                new com.google.zxing.qrcode.QRCodeWriter();
            com.google.zxing.common.BitMatrix bm =
                writer.encode(qrText,
                    com.google.zxing.BarcodeFormat.QR_CODE, qrSize, qrSize, hints);
            javafx.scene.image.WritableImage wi =
                new javafx.scene.image.WritableImage(qrSize, qrSize);
            javafx.scene.image.PixelWriter pw = wi.getPixelWriter();
            for (int x = 0; x < qrSize; x++)
                for (int y = 0; y < qrSize; y++)
                    pw.setColor(x, y, bm.get(x, y)
                        ? Color.web("#1B1F3B") : Color.WHITE);
            qrView.setImage(wi);
        } catch (Exception ignored) {}
        qrView.setFitWidth(160);
        qrView.setFitHeight(160);

        Label qrLabel = new Label("Scan for resident info");
        qrLabel.setStyle("-fx-font-size:10px;-fx-text-fill:#7a7a8a;");
        qrLabel.setMaxWidth(180);
        qrLabel.setAlignment(Pos.CENTER);

        VBox qrBox = new VBox(6, qrView, qrLabel);
        qrBox.setAlignment(Pos.CENTER);
        qrBox.setStyle(
            "-fx-background-color:white;-fx-border-color:#e8e6e3;" +
            "-fx-border-width:0.5;-fx-border-radius:8;-fx-background-radius:8;" +
            "-fx-padding:12;");
        qrBox.setMinWidth(190);
        qrBox.setMaxWidth(190);

        Label statusPill = new Label(r.getStatus());
        statusPill.getStyleClass().addAll("status-pill",
            switch (r.getStatus().toLowerCase()) {
                case "active"   -> "status-active";
                case "pending"  -> "status-pending";
                case "inactive" -> "status-open";
                default         -> "status-pending";
            });

        Label nameLabel = new Label(r.getFullName());
        nameLabel.setStyle("-fx-font-size:20px;-fx-font-weight:bold;-fx-text-fill:#1B1F3B;");
        nameLabel.setWrapText(false);
        nameLabel.setMaxWidth(300);

        Label subLabel = new Label(
            r.getOccupation().isBlank() ? "Resident" : r.getOccupation());
        subLabel.setStyle("-fx-font-size:13px;-fx-text-fill:#7a7a8a;");

        VBox nameBox = new VBox(5, nameLabel, subLabel, statusPill);
        nameBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(nameBox, Priority.ALWAYS);

        HBox topLeft = new HBox(16, avatar, nameBox);
        topLeft.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(topLeft, Priority.ALWAYS);

        HBox headerBox = new HBox(20, topLeft, qrBox);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(topLeft, Priority.ALWAYS);
        headerBox.setPadding(new Insets(0, 0, 18, 0));
        headerBox.setStyle(
            "-fx-border-color:transparent transparent #e8e6e3 transparent;" +
            "-fx-border-width:0 0 1 0;");

        String[][] rows = {
            {"Gender",            r.getGender()},
            {"Civil Status",      r.getCivilStatus()},
            {"Birthdate",         r.getBirthdate()},
            {"Address",           r.getAddress()},
            {"Contact No.",       r.getContactNo()},
            {"Email",             r.getEmail().isBlank()            ? "—" : r.getEmail()},
            {"Occupation",        r.getOccupation().isBlank()       ? "—" : r.getOccupation()},
            {"Emergency Contact", r.getEmergencyContact().isBlank() ? "—" : r.getEmergencyContact()},
            {"Status",            r.getStatus()},
        };

        VBox infoBox = new VBox(0);
        infoBox.setPadding(new Insets(16, 0, 0, 0));

        for (int i = 0; i < rows.length; i++) {
            Label key = new Label(rows[i][0]);
            key.setStyle(
                "-fx-font-size:11px;-fx-font-weight:bold;" +
                "-fx-text-fill:#9a9aaa;-fx-min-width:160;-fx-pref-width:160;");
            Label val = new Label(rows[i][1]);
            val.setStyle("-fx-font-size:13px;-fx-text-fill:#1B1F3B;");
            val.setWrapText(true);
            val.setMaxWidth(340);
            HBox row = new HBox(16, key, val);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(10, 4, 10, 4));
            infoBox.getChildren().add(row);
            if (i < rows.length - 1) {
                Region sep = new Region();
                sep.setMinHeight(1); sep.setMaxHeight(1); sep.setPrefHeight(1);
                sep.setStyle("-fx-background-color:#f0f0f0;");
                infoBox.getChildren().add(sep);
            }
        }

        Button btnEdit  = new Button("Edit resident");
        Button btnClose = new Button("Close");
        Button btnID    = new Button("🪪  Generate ID");
        btnEdit .getStyleClass().add("btn-primary");
        btnClose.getStyleClass().add("btn-ghost");
        btnID   .getStyleClass().add("btn-id");

        btnClose.setOnAction(e -> modal.close());
        btnEdit .setOnAction(e -> { modal.close(); showResidentModal("Edit Resident", r); });
        btnID   .setOnAction(e -> showResidentID(r));

        HBox footer = new HBox(10, btnClose, btnID, btnEdit);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(18, 0, 0, 0));
        footer.setStyle(
            "-fx-border-color:#e8e6e3 transparent transparent transparent;" +
            "-fx-border-width:1 0 0 0;");

        VBox root = new VBox(headerBox, infoBox, footer);
        root.setPadding(new Insets(24));
        root.setStyle("-fx-background-color:#FAF9F6;");
        root.setPrefWidth(600);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        modal.setScene(scene);
        modal.showAndWait();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  RESIDENT ID CARD GENERATOR
    // ════════════════════════════════════════════════════════════════════════
    private void showResidentID(Resident r) {
        Stage idStage = new Stage();
        idStage.initModality(Modality.APPLICATION_MODAL);
        idStage.initStyle(StageStyle.UTILITY);
        idStage.setTitle("Resident ID — " + r.getFullName());
        idStage.setResizable(false);

        // ── QR Code (same data as profile) ──────────────────────────────
        String qrText =
            "BARANGAY 143 - RESIDENT ID\n" +
            "Name:    " + r.getFullName()    + "\n" +
            "Address: " + r.getAddress()     + "\n" +
            "Contact: " + r.getContactNo()   + "\n" +
            "Status:  " + r.getStatus()      + "\n" +
            "Barangay 143, Olongapo City";

        ImageView qrView = new ImageView();
        try {
            int qrSize = 200;
            java.util.Map<com.google.zxing.EncodeHintType, Object> hints = new java.util.HashMap<>();
            hints.put(com.google.zxing.EncodeHintType.ERROR_CORRECTION,
                com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.M);
            hints.put(com.google.zxing.EncodeHintType.MARGIN, 1);
            com.google.zxing.qrcode.QRCodeWriter writer = new com.google.zxing.qrcode.QRCodeWriter();
            com.google.zxing.common.BitMatrix bm =
                writer.encode(qrText, com.google.zxing.BarcodeFormat.QR_CODE, qrSize, qrSize, hints);
            javafx.scene.image.WritableImage wi = new javafx.scene.image.WritableImage(qrSize, qrSize);
            javafx.scene.image.PixelWriter pw = wi.getPixelWriter();
            for (int x = 0; x < qrSize; x++)
                for (int y = 0; y < qrSize; y++)
                    pw.setColor(x, y, bm.get(x, y)
                        ? javafx.scene.paint.Color.web("#1B1F3B")
                        : javafx.scene.paint.Color.WHITE);
            qrView.setImage(wi);
        } catch (Exception ignored) {}
        qrView.setFitWidth(90);
        qrView.setFitHeight(90);

        // ── Avatar ──────────────────────────────────────────────────────
        String imgFile;
        String rName = r.getFullName().trim().toLowerCase();
        if (rName.equals("drey custodio"))       imgFile = "drey.jpg";
        else if (rName.equals("cristopher dabu")) imgFile = "dabs.jpg";
        else if (rName.equals("kristopher edora"))imgFile = "tope.jpg";
        else imgFile = "Female".equalsIgnoreCase(r.getGender()) ? "female.png" : "male.png";

        ImageView photo = new ImageView();
        try {
            java.net.URL imgUrl = getClass().getResource(imgFile);
            if (imgUrl != null) photo.setImage(new Image(imgUrl.toExternalForm()));
        } catch (Exception ignored) {}
        photo.setFitWidth(80);
        photo.setFitHeight(90);
        photo.setPreserveRatio(false);

        // Photo border frame
        StackPane photoFrame = new StackPane(photo);
        photoFrame.setStyle(
            "-fx-border-color:#E6B8C2;-fx-border-width:2;" +
            "-fx-background-color:white;-fx-padding:3;");
        photoFrame.setMinWidth(86); photoFrame.setMaxWidth(86);
        photoFrame.setMinHeight(96); photoFrame.setMaxHeight(96);

        // ── ID Card layout ───────────────────────────────────────────────
        // HEADER — dark banner
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10, 14, 10, 14));
        header.setStyle("-fx-background-color:#1B1F3B;");

        // Logo in header
        ImageView logoSmall = new ImageView();
        try {
            java.net.URL logoUrl = getClass().getResource("logo.png");
            if (logoUrl != null) logoSmall.setImage(new Image(logoUrl.toExternalForm()));
        } catch (Exception ignored) {}
        logoSmall.setFitWidth(36); logoSmall.setFitHeight(36); logoSmall.setPreserveRatio(true);

        Label brgyTitle = new Label("REPUBLIKA NG PILIPINAS");
        brgyTitle.setStyle("-fx-font-size:8px;-fx-text-fill:rgba(230,184,194,0.80);-fx-font-weight:bold;");
        Label brgyName  = new Label("Barangay 143");
        brgyName.setStyle("-fx-font-size:15px;-fx-text-fill:white;-fx-font-weight:bold;");
        Label brgyCity  = new Label("Olongapo City, Zambales");
        brgyCity.setStyle("-fx-font-size:9px;-fx-text-fill:rgba(250,249,246,0.65);");

        VBox titleBox = new VBox(1, brgyTitle, brgyName, brgyCity);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(titleBox, Priority.ALWAYS);

        Label idTitle = new Label("RESIDENT\nIDENTIFICATION\nCARD");
        idTitle.setStyle(
            "-fx-font-size:9px;-fx-font-weight:bold;" +
            "-fx-text-fill:#E6B8C2;-fx-text-alignment:right;");
        idTitle.setAlignment(Pos.CENTER_RIGHT);

        header.getChildren().addAll(logoSmall, titleBox, idTitle);

        // BODY
        // Resident name + designation
        Label nameLabel = new Label(r.getFullName().toUpperCase());
        nameLabel.setStyle(
            "-fx-font-size:14px;-fx-font-weight:bold;-fx-text-fill:#1B1F3B;");
        nameLabel.setWrapText(false);
        nameLabel.setMaxWidth(220);

        Label desigLabel = new Label(r.getOccupation().isBlank() ? "Resident" : r.getOccupation());
        desigLabel.setStyle("-fx-font-size:10px;-fx-text-fill:#7a7a8a;");

        // Status pill
        Label statusPill = new Label(r.getStatus());
        statusPill.getStyleClass().addAll("status-pill",
            switch (r.getStatus().toLowerCase()) {
                case "active"   -> "status-active";
                case "pending"  -> "status-pending";
                case "inactive" -> "status-open";
                default         -> "status-pending";
            });

        // ID number (generated from resident ID + today)
        String idNo = "BRY143-" + String.format("%05d", r.getId()) + "-" +
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        Label idNoLabel = new Label("ID No: " + idNo);
        idNoLabel.setStyle(
            "-fx-font-size:9px;-fx-text-fill:#1B1F3B;-fx-font-weight:bold;");

        VBox nameCol = new VBox(4, nameLabel, desigLabel, statusPill, idNoLabel);
        nameCol.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(nameCol, Priority.ALWAYS);

        HBox photoRow = new HBox(12, photoFrame, nameCol);
        photoRow.setAlignment(Pos.TOP_LEFT);
        photoRow.setPadding(new Insets(14, 14, 8, 14));

        // Info fields
        String[][] fields = {
            {"Address",  r.getAddress()},
            {"Birthdate", r.getBirthdate()},
            {"Contact",  r.getContactNo()},
            {"Gender",   r.getGender()},
            {"Civil Status", r.getCivilStatus()},
        };

        VBox infoGrid = new VBox(0);
        infoGrid.setPadding(new Insets(0, 14, 10, 14));
        for (String[] f : fields) {
            HBox row = new HBox(8);
            row.setPadding(new Insets(4, 0, 4, 0));
            row.setStyle("-fx-border-color:transparent transparent #F0F2F5 transparent;-fx-border-width:0 0 1 0;");
            Label k = new Label(f[0] + ":");
            k.setStyle("-fx-font-size:9px;-fx-font-weight:bold;-fx-text-fill:#9a9aaa;-fx-min-width:70;");
            Label v = new Label(f[1].isBlank() ? "—" : f[1]);
            v.setStyle("-fx-font-size:10px;-fx-text-fill:#1B1F3B;-fx-font-weight:bold;");
            row.getChildren().addAll(k, v);
            infoGrid.getChildren().add(row);
        }

        // FOOTER — pink banner with QR
        VBox qrCol = new VBox(3, qrView);
        qrCol.setAlignment(Pos.CENTER);
        qrCol.setStyle("-fx-background-color:white;-fx-padding:4;-fx-border-color:#e8e6e3;-fx-border-width:0.5;");

        Label scanNote = new Label("Scan to verify");
        scanNote.setStyle("-fx-font-size:7px;-fx-text-fill:rgba(250,249,246,0.70);");

        Label emergLabel = new Label("IN CASE OF EMERGENCY, PLEASE CONTACT:");
        emergLabel.setStyle("-fx-font-size:8px;-fx-text-fill:rgba(250,249,246,0.70);-fx-font-weight:bold;");
        Label emergVal = new Label(r.getEmergencyContact().isBlank() ? "—" : r.getEmergencyContact());
        emergVal.setStyle("-fx-font-size:10px;-fx-text-fill:white;-fx-font-weight:bold;");
        emergVal.setWrapText(true); emergVal.setMaxWidth(200);

        Label validLabel = new Label("Valid until December 31, " + LocalDate.now().getYear());
        validLabel.setStyle("-fx-font-size:8px;-fx-text-fill:rgba(250,249,246,0.55);");

        VBox footerLeft = new VBox(4, emergLabel, emergVal, validLabel);
        footerLeft.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(footerLeft, Priority.ALWAYS);

        VBox footerRight = new VBox(4, qrCol, scanNote);
        footerRight.setAlignment(Pos.CENTER);

        HBox footer2 = new HBox(10, footerLeft, footerRight);
        footer2.setAlignment(Pos.CENTER_LEFT);
        footer2.setPadding(new Insets(10, 14, 12, 14));
        footer2.setStyle("-fx-background-color:#1B1F3B;");

        // ── Assemble Card ────────────────────────────────────────────────
        VBox card = new VBox(header, photoRow, infoGrid, footer2);
        card.setStyle(
            "-fx-background-color:white;" +
            "-fx-border-color:#E6B8C2;" +
            "-fx-border-width:1.5;" +
            "-fx-border-radius:10;" +
            "-fx-background-radius:10;" +
            "-fx-effect: dropshadow(gaussian,rgba(27,31,59,0.20),16,0,0,4);");
        card.setPrefWidth(360);
        card.setMaxWidth(360);

        // ── Outer wrapper ────────────────────────────────────────────────
        VBox wrapper = new VBox(16);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPadding(new Insets(20));
        wrapper.setStyle("-fx-background-color:#F0F2F5;");

        // Preview label
        Label previewLbl = new Label("ID Card Preview");
        previewLbl.setStyle("-fx-font-size:13px;-fx-font-weight:bold;-fx-text-fill:#1B1F3B;");

        Label noteLbl = new Label("Present this ID to Barangay 143 officials for verification.");
        noteLbl.setStyle("-fx-font-size:11px;-fx-text-fill:#7a7a8a;");
        noteLbl.setWrapText(true);
        noteLbl.setMaxWidth(360);

        // Buttons
        Button btnPrint = new Button("🖨  Print / Save");
        Button btnDone  = new Button("Close");
        btnPrint.getStyleClass().add("btn-primary");
        btnDone .getStyleClass().add("btn-ghost");

        btnDone .setOnAction(ev -> idStage.close());
        btnPrint.setOnAction(ev -> {
            // Print using JavaFX PrinterJob
            javafx.print.PrinterJob job = javafx.print.PrinterJob.createPrinterJob();
            if (job != null) {
                boolean ok = job.showPrintDialog(idStage);
                if (ok) {
                    boolean printed = job.printPage(card);
                    if (printed) job.endJob();
                }
            }
        });

        HBox btnRow = new HBox(10, btnDone, btnPrint);
        btnRow.setAlignment(Pos.CENTER_RIGHT);
        btnRow.setMaxWidth(360);

        wrapper.getChildren().addAll(previewLbl, card, noteLbl, btnRow);

        Scene scene = new Scene(wrapper);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        idStage.setScene(scene);
        idStage.showAndWait();
    }
    
    // ════════════════════════════════════════════════════════════════════════
    //  ADD / EDIT ENTRY POINTS
    // ════════════════════════════════════════════════════════════════════════
    @FXML private void openAddModal()          { showResidentModal("Add New Resident", null); }
    @FXML private void openAddIncidentModal()  { showIncidentModal("Report Incident",  null); }
    @FXML private void openAddInventoryModal() { showInventoryModal("Add Item",        null); }

    private void openEditResidentModal(Resident r)       { showResidentModal ("Edit Resident", r); }
    private void openEditIncidentModal(Incident i)       { showIncidentModal ("Edit Incident", i); }
    private void openEditInventoryModal(InventoryItem v) { showInventoryModal("Edit Item",     v); }

    // ════════════════════════════════════════════════════════════════════════
    //  RESIDENT MODAL
    // ════════════════════════════════════════════════════════════════════════
    private void showResidentModal(String title, Resident existing) {
        Stage modal = buildStage(title);
        GridPane grid = buildGrid(130, 240);

        TextField        fName   = styledField("e.g. Maria Santos");
        TextField        fAddr   = styledField("Purok, Block, Street");
        TextField        fBirth  = styledField("YYYY-MM-DD");
        TextField        fPhone  = styledField("09XXXXXXXXXX");
        TextField        fEmail  = styledField("email@example.com");
        TextField        fOccup  = styledField("e.g. Teacher");
        TextField        fEmerg  = styledField("Name and contact number");
        ComboBox<String> cGender = styledCombo("Male", "Female");
        ComboBox<String> cCivil  = styledCombo("Single","Married","Widowed","Separated");
        ComboBox<String> cStatus = styledCombo("Active", "Pending", "Inactive");

        if (existing != null) {
            fName .setText(existing.getFullName());
            fAddr .setText(existing.getAddress());
            fBirth.setText(existing.getBirthdate());
            fPhone.setText(existing.getContactNo());
            fEmail.setText(existing.getEmail());
            fOccup.setText(existing.getOccupation());
            fEmerg.setText(existing.getEmergencyContact());
            cGender.setValue(existing.getGender());
            cCivil .setValue(existing.getCivilStatus());
            cStatus.setValue(existing.getStatus());
        } else {
            cGender.setValue("Male");
            cCivil .setValue("Single");
            cStatus.setValue("Active");
        }

        addRowField(grid, 0, "Full name",        fName);
        addRowNode (grid, 1, "Gender",            cGender);
        addRowNode (grid, 2, "Civil status",      cCivil);
        addRowField(grid, 3, "Birthdate",         fBirth);
        addRowField(grid, 4, "Address",           fAddr);
        addRowField(grid, 5, "Contact no.",       fPhone);
        addRowField(grid, 6, "Email",             fEmail);
        addRowField(grid, 7, "Occupation",        fOccup);
        addRowField(grid, 8, "Emergency contact", fEmerg);
        addRowNode (grid, 9, "Status",            cStatus);

        Label err = errLabel();
        grid.add(err, 0, 10, 2, 1);

        Button save   = new Button(existing == null ? "Save resident" : "Update resident");
        Button cancel = new Button("Cancel");
        save  .getStyleClass().add("btn-primary");
        cancel.getStyleClass().add("btn-ghost");

        cancel.setOnAction(e -> modal.close());
        save.setOnAction(e -> {
            if (fName.getText().isBlank()) { err.setText("Full name is required."); return; }
            if (fAddr.getText().isBlank()) { err.setText("Address is required.");   return; }
            if (!fBirth.getText().isBlank() &&
                !fBirth.getText().matches("\\d{4}-\\d{2}-\\d{2}"))
                { err.setText("Birthdate must be YYYY-MM-DD."); return; }
            if (!fPhone.getText().isBlank() &&
                !fPhone.getText().matches("09\\d{9}"))
                { err.setText("Contact must be 09XXXXXXXXXX."); return; }
            try {
                if (existing == null)
                    ResidentService.insert(
                        fName.getText().trim(), fAddr.getText().trim(),
                        fBirth.getText().trim(), fPhone.getText().trim(),
                        cStatus.getValue(), cGender.getValue(),
                        cCivil.getValue(), fOccup.getText().trim(),
                        fEmail.getText().trim(), fEmerg.getText().trim());
                else
                    ResidentService.update(
                        existing.getId(),
                        fName.getText().trim(), fAddr.getText().trim(),
                        fBirth.getText().trim(), fPhone.getText().trim(),
                        cStatus.getValue(), cGender.getValue(),
                        cCivil.getValue(), fOccup.getText().trim(),
                        fEmail.getText().trim(), fEmerg.getText().trim());
                modal.close();
                loadResidents();
                refreshDashboardMetrics();
                showSuccess("Resident saved successfully.");
            } catch (SQLException ex) { err.setText("Database error: " + ex.getMessage()); }
        });
        showModal(modal, grid, cancel, save);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  INCIDENT MODAL
    // ════════════════════════════════════════════════════════════════════════
    private void showIncidentModal(String title, Incident existing) {
        Stage modal = buildStage(title);
        GridPane grid = buildGrid(120, 250);

        TextField        fDesc   = styledField("Brief description");
        TextField        fLoc    = styledField("e.g. Purok 2, Blk 4");
        TextField        fBy     = styledField("Resident name");
        ComboBox<String> cStatus = styledCombo("Open", "Pending", "Resolved");

        if (existing != null) {
            fDesc.setText(existing.getDescription());
            fLoc .setText(existing.getLocation());
            fBy  .setText(existing.getReportedBy());
            cStatus.setValue(existing.getStatus());
        } else { cStatus.setValue("Open"); }

        addRowField(grid, 0, "Description", fDesc);
        addRowField(grid, 1, "Location",    fLoc);
        addRowField(grid, 2, "Reported by", fBy);
        addRowNode (grid, 3, "Status",      cStatus);

        Label err = errLabel();
        grid.add(err, 0, 4, 2, 1);

        Button save   = new Button(existing == null ? "Submit" : "Update incident");
        Button cancel = new Button("Cancel");
        save  .getStyleClass().add("btn-primary");
        cancel.getStyleClass().add("btn-ghost");

        cancel.setOnAction(e -> modal.close());
        save.setOnAction(e -> {
            if (fDesc.getText().isBlank()) { err.setText("Description is required."); return; }
            if (fLoc .getText().isBlank()) { err.setText("Location is required.");    return; }
            try {
                if (existing == null)
                    IncidentService.insert(fDesc.getText().trim(), fLoc.getText().trim(),
                        fBy.getText().trim(), cStatus.getValue());
                else
                    IncidentService.update(existing.getId(), fDesc.getText().trim(),
                        fLoc.getText().trim(), fBy.getText().trim(), cStatus.getValue());
                modal.close();
                loadIncidents();
                refreshDashboardMetrics();
                showSuccess("Incident saved successfully.");
            } catch (SQLException ex) { err.setText("Database error: " + ex.getMessage()); }
        });
        showModal(modal, grid, cancel, save);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  INVENTORY MODAL
    // ════════════════════════════════════════════════════════════════════════
    private void showInventoryModal(String title, InventoryItem existing) {
        Stage modal = buildStage(title);
        GridPane grid = buildGrid(130, 240);

        TextField        fName  = styledField("e.g. First Aid Kits");
        TextField        fQty   = styledField("0");
        TextField        fLoc   = styledField("e.g. Storage A");
        TextField        fNotes = styledField("Optional notes");
        ComboBox<String> cStatus =
            styledCombo("In Stock","Low Stock","Out of Stock","Restocked");

        if (existing != null) {
            fName .setText(existing.getItemName());
            fQty  .setText(String.valueOf(existing.getQuantity()));
            fLoc  .setText(existing.getLocation());
            fNotes.setText(existing.getNotes());
            cStatus.setValue(existing.getStockStatus());
        } else { cStatus.setValue("In Stock"); }

        addRowField(grid, 0, "Item name",    fName);
        addRowField(grid, 1, "Quantity",     fQty);
        addRowNode (grid, 2, "Stock status", cStatus);
        addRowField(grid, 3, "Location",     fLoc);
        addRowField(grid, 4, "Notes",        fNotes);

        Label err = errLabel();
        grid.add(err, 0, 5, 2, 1);

        Button save   = new Button(existing == null ? "Add item" : "Update item");
        Button cancel = new Button("Cancel");
        save  .getStyleClass().add("btn-primary");
        cancel.getStyleClass().add("btn-ghost");

        cancel.setOnAction(e -> modal.close());
        save.setOnAction(e -> {
            if (fName.getText().isBlank()) { err.setText("Item name is required."); return; }
            int qty;
            try { qty = Integer.parseInt(fQty.getText().trim()); }
            catch (NumberFormatException ex) { err.setText("Quantity must be a number."); return; }
            if (qty < 0) { err.setText("Quantity cannot be negative."); return; }
            try {
                if (existing == null)
                    InventoryService.insert(fName.getText().trim(), qty,
                        cStatus.getValue(), fLoc.getText().trim(), fNotes.getText().trim());
                else
                    InventoryService.update(existing.getId(), fName.getText().trim(), qty,
                        cStatus.getValue(), fLoc.getText().trim(), fNotes.getText().trim());
                modal.close();
                loadInventory();
                showSuccess("Inventory item saved successfully.");
            } catch (SQLException ex) { err.setText("Database error: " + ex.getMessage()); }
        });
        showModal(modal, grid, cancel, save);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  DELETE HANDLERS
    // ════════════════════════════════════════════════════════════════════════
    private void deleteResident(Resident r) {
        if (!confirmDelete(r.getFullName())) return;
        try { ResidentService.delete(r.getId()); loadResidents(); refreshDashboardMetrics(); }
        catch (SQLException e) { showError("Failed to delete: " + e.getMessage()); }
    }

    private void deleteIncident(Incident i) {
        if (!confirmDelete(i.getDescription())) return;
        try { IncidentService.delete(i.getId()); loadIncidents(); refreshDashboardMetrics(); }
        catch (SQLException e) { showError("Failed to delete: " + e.getMessage()); }
    }

    private void deleteInventoryItem(InventoryItem v) {
        if (!confirmDelete(v.getItemName())) return;
        try { InventoryService.delete(v.getId()); loadInventory(); }
        catch (SQLException e) { showError("Failed to delete: " + e.getMessage()); }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  SETTINGS
    // ════════════════════════════════════════════════════════════════════════
    @FXML private void handleChangePassword() {
        String current = txtCurrentPass.getText();
        String newPass = txtNewPass    .getText();
        String confirm = txtConfirmPass.getText();
        if (current.isEmpty() || newPass.isEmpty() || confirm.isEmpty())
            { showSettingsMsg("All fields are required.", false); return; }
        if (!LoginController.ADMIN_PASS.equals(current))
            { showSettingsMsg("Current password is incorrect.", false); return; }
        if (newPass.length() < 6)
            { showSettingsMsg("New password must be at least 6 characters.", false); return; }
        if (!newPass.equals(confirm))
            { showSettingsMsg("Passwords do not match.", false); return; }
        LoginController.ADMIN_PASS = newPass;
        txtCurrentPass.clear(); txtNewPass.clear(); txtConfirmPass.clear();
        showSettingsMsg("Password updated successfully.", true);
    }

    private void showSettingsMsg(String msg, boolean success) {
        lblSettingsMsg.setText(msg);
        lblSettingsMsg.setStyle(success
            ? "-fx-text-fill:#27500a;-fx-background-color:#eaf3de;" +
              "-fx-border-color:#639922;-fx-border-width:0.5;" +
              "-fx-border-radius:6;-fx-background-radius:6;-fx-padding:8 12 8 12;"
            : "-fx-text-fill:#a32d2d;-fx-background-color:#fcebeb;" +
              "-fx-border-color:#e24b4a;-fx-border-width:0.5;" +
              "-fx-border-radius:6;-fx-background-radius:6;-fx-padding:8 12 8 12;");
        lblSettingsMsg.setVisible(true);
        lblSettingsMsg.setManaged(true);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MODAL HELPERS
    // ════════════════════════════════════════════════════════════════════════
    private Stage buildStage(String title) {
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initStyle(StageStyle.UTILITY);
        s.setTitle(title);
        s.setResizable(false);
        return s;
    }

    private GridPane buildGrid(int col1, int col2) {
        GridPane g = new GridPane();
        g.setHgap(10); g.setVgap(12); g.setPadding(new Insets(20));
        g.getColumnConstraints().addAll(
            new ColumnConstraints(col1), new ColumnConstraints(col2));
        return g;
    }

    private TextField styledField(String prompt) {
        TextField f = new TextField();
        f.setPromptText(prompt);
        f.getStyleClass().add("modal-field");
        return f;
    }

    private ComboBox<String> styledCombo(String... options) {
        ComboBox<String> cb = new ComboBox<>();
        cb.getItems().addAll(options);
        cb.getStyleClass().add("modal-field");
        cb.setMaxWidth(Double.MAX_VALUE);
        return cb;
    }

    private void addRowField(GridPane g, int row, String text, TextField field) {
        Label l = new Label(text); l.getStyleClass().add("modal-label");
        g.add(l, 0, row); g.add(field, 1, row);
    }

    private void addRowNode(GridPane g, int row, String text, javafx.scene.Node node) {
        Label l = new Label(text); l.getStyleClass().add("modal-label");
        g.add(l, 0, row); g.add(node, 1, row);
    }

    private Label errLabel() {
        Label l = new Label(""); l.getStyleClass().add("error-label"); return l;
    }

    private void showModal(Stage modal, GridPane grid, Button cancel, Button save) {
        HBox footer = new HBox(8, cancel, save);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(8, 20, 16, 20));
        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color:transparent;-fx-background:#FAF9F6;");
        VBox root = new VBox(scroll, footer);
        root.setStyle("-fx-background-color:#FAF9F6;");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        modal.setScene(scene);
        modal.showAndWait();
    }

    private boolean confirmDelete(String name) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
            "Delete \"" + name + "\"? This cannot be undone.",
            ButtonType.YES, ButtonType.NO);
        a.setHeaderText(null); a.setTitle("Confirm delete");
        return a.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    private void showSuccess(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Success"); a.setHeaderText(null); a.setContentText(msg); a.show();
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error"); a.setHeaderText(null); a.setContentText(msg); a.show();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  AVATAR HELPERS
    // ════════════════════════════════════════════════════════════════════════
    private String getInitials(String name) {
        if (name == null || name.isBlank()) return "?";
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) return parts[0].substring(0, 1).toUpperCase();
        return (parts[0].substring(0, 1) +
                parts[parts.length - 1].substring(0, 1)).toUpperCase();
    }

    private String getAvatarColor(String name) {
        String[] colors = {
            "#4A90D9","#7B68EE","#E67E73","#F0A500",
            "#27AE60","#E91E8C","#00BCD4","#FF7043"
        };
        return colors[Math.abs(name.hashCode()) % colors.length];
    }

    // ════════════════════════════════════════════════════════════════════════
    //  CELL RENDERERS
    // ════════════════════════════════════════════════════════════════════════
    private static class StatusBadgeCell<T> extends TableCell<T, String> {
        @Override protected void updateItem(String val, boolean empty) {
            super.updateItem(val, empty);
            if (empty || val == null) { setGraphic(null); setText(null); return; }
            Label pill = new Label(val);
            pill.getStyleClass().addAll("status-pill",
                switch (val.toLowerCase()) {
                    case "active"   -> "status-active";
                    case "pending"  -> "status-pending";
                    case "resolved" -> "status-resolved";
                    case "open"     -> "status-open";
                    default         -> "status-pending";
                });
            setGraphic(pill); setText(null);
        }
    }

    private static class InventoryStatusCell extends TableCell<InventoryItem, String> {
        @Override protected void updateItem(String val, boolean empty) {
            super.updateItem(val, empty);
            if (empty || val == null) { setGraphic(null); setText(null); return; }
            Label pill = new Label(val);
            pill.getStyleClass().addAll("status-pill",
                switch (val.toLowerCase()) {
                    case "in stock"     -> "status-active";
                    case "low stock"    -> "status-pending";
                    case "out of stock" -> "status-open";
                    case "restocked"    -> "status-resolved";
                    default             -> "status-pending";
                });
            setGraphic(pill); setText(null);
        }
    }

    private static class ActionMenuCell<T> extends TableCell<T, Void> {
        private final Consumer<T> onEdit;
        private final Consumer<T> onDelete;
        private final Button btn = new Button("⋮");

        ActionMenuCell(Consumer<T> onEdit, Consumer<T> onDelete) {
            this.onEdit = onEdit; this.onDelete = onDelete;
            btn.getStyleClass().add("menu-dot-btn");
        }

        @Override protected void updateItem(Void v, boolean empty) {
            super.updateItem(v, empty);
            if (empty) { setGraphic(null); return; }
            btn.setOnAction(e -> {
                @SuppressWarnings("unchecked") T item = (T) getTableRow().getItem();
                if (item == null) return;
                ContextMenu menu    = new ContextMenu();
                MenuItem editItem   = new MenuItem("Edit");
                MenuItem deleteItem = new MenuItem("Delete");
                editItem  .setOnAction(ev -> onEdit  .accept(item));
                deleteItem.setOnAction(ev -> onDelete.accept(item));
                menu.getItems().addAll(editItem, new SeparatorMenuItem(), deleteItem);
                menu.show(btn, javafx.geometry.Side.BOTTOM, 0, 0);
            });
            setGraphic(btn);
        }
    }
}