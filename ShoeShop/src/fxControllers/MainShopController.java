package fxControllers;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import hibernateControllers.UserHib;
import hibernateControllers.GenericHib;
import hibernateControllers.WarehouseHib;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import javafx.collections.ObservableList;
import hibernateControllers.CustomHib;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MainShopController implements Initializable {

    @FXML
    public ListView<Product> productList;
    @FXML
    public ListView<Product> currentOrder;
    @FXML
    public Tab usersTab;
    @FXML
    public Tab warehouseTab;
    @FXML
    public ListView<Warehouse> warehouseList;
    @FXML
    public TextField addressWarehouseField;
    @FXML
    public TextField titleWarehouseField;
    @FXML
    public Tab ordersTab;
    @FXML
    public Tab productsTab;
    @FXML
    public TableView<Manager> managerTable;
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab primaryTab;
    @FXML
    public ListView<Product> productListManager;
    @FXML
    public TextField productTitleField;
    @FXML
    public TextArea productDescriptionField;
    @FXML
    public TextField productPriceField;
    @FXML
    public TextField productSizeField;
    @FXML
    public TextField productMaterialField;
    @FXML
    public TextField productFeaturesField;
    @FXML
    public ComboBox<Productsex> productSexC;
    @FXML
    public TextField productColorField;
    @FXML
    public ComboBox<ProductSType> productStypeC;
    @FXML
    public ComboBox<ProductType> productType;
    @FXML
    public ComboBox<Warehouse> warehouseComboBox;
    @FXML
    public TextField productManufacturerField;
    @FXML
    public Label totalAmountLabel;
    public Tab commentsTab;
    @FXML
    public ListView<Comment> commentList;
    @FXML
    public TextArea commentBodyField;
    @FXML
    public ComboBox<Integer> commentRatingField;
    @FXML
    public ComboBox<Product> productComboBox;
    public TextField OrderValueField;
    public TextField CustomerIdField;
    public Tab statisticsTab;
    public TableView<Cart> cartData;
    public TableColumn<Cart, Integer> buyerColumn;
    public TableColumn<Cart, LocalDate> dateColumn;
    public TableColumn<Cart, Double> valueColumn;
    public TableColumn<Cart, Integer> userColumn;
    public ListView priceView;
    public TextField minCostField;
    public TextField maxCostField;
    public TextField userIdField;
    public DatePicker fromField;
    private CustomHib customHib;
    public DatePicker toField;
    public PieChart cartValuePieChart;
    public TableColumn<Cart, Boolean> completedColumn;
    @FXML
    private CheckBox orderCompletedCheckBox;
    public TextArea messageInputField;
    public Tab messageTab;
    public TableView<Message> messageTable;
    public TableColumn<Message, Integer> messageidColumn;
    public TableColumn<Message, LocalDate> messageCreatedColumn;
    public TableColumn<Message, String> messageContenColumn;
    public TableColumn<Message, Integer> messageCarIdColumn;
    public TextArea messageResponseField;
    @FXML
    private Button commentDeleteButton;
    @FXML
    private TableView<Manager> managerTableView;

    @FXML
    private TableColumn<Manager, String> managerNameColumn;

    @FXML
    private TableColumn<Manager, String> managerSurnameColumn;

    @FXML
    private TableColumn<Manager, String> managerLoginColumn;

    @FXML
    private TableColumn<Manager, LocalDate> managerBirthdateColumn;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerSurnameColumn;

    @FXML
    private TableColumn<Customer, String> customerLoginColumn;

    @FXML
    private TableColumn<Customer, LocalDate> customerBirthdateColumn;

    @FXML
    private TableView<Cart> cartTable;

    @FXML
    private TableColumn<Cart, Integer> idColumn;

    @FXML
    private TableColumn<Cart, LocalDate> dateCreatedColumn;

    @FXML
    private TableColumn<Cart, Double> cartValueColumn;

    @FXML
    private TableColumn<Cart, Integer> userIdColumn;

    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private GenericHib genericHib;

    private UserHib userHib;


    private WarehouseHib warehouseHib;



    public void setData(EntityManagerFactory entityManagerFactory, User currentUser) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = currentUser;


        this.genericHib = new GenericHib(entityManagerFactory);
        this.userHib = new UserHib(entityManagerFactory);
        this.warehouseHib = new WarehouseHib(entityManagerFactory);

        limitAccess();
        loadData();
    }

    private void loadData() {
        genericHib = new GenericHib(entityManagerFactory);
        productList.getItems().clear();
        productList.getItems().addAll(genericHib.getAllRecords(Product.class));

    }


    public void setupCommentRatingField() {

        commentRatingField.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5));
    }

    private void limitAccess() {
        if (currentUser.getClass() == Manager.class) {
            Manager manager = (Manager) currentUser;

            if (!manager.isAdmin()) {
                managerTable.setDisable(true);
            }
        } else if (currentUser.getClass() == Customer.class) {
            Customer customer = (Customer) currentUser;
            commentDeleteButton.setDisable(true);
            commentDeleteButton.setDisable(true);
            CustomerIdField.setDisable(true);
            OrderValueField.setDisable(true);
            orderCompletedCheckBox.setDisable(true);
            messageInputField.setDisable(true);
            tabPane.getTabs().remove(usersTab);
            tabPane.getTabs().remove(warehouseTab);
            tabPane.getTabs().remove(productsTab);
            tabPane.getTabs().remove(statisticsTab);
        } else {
            JavaFxCustomUtils.showAlert("Unauthorized Access", "You do not have permission to access this feature." );
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        managerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        managerSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        managerLoginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        managerBirthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        customerLoginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        customerBirthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        cartValueColumn.setCellValueFactory(new PropertyValueFactory<>("cart_value"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        completedColumn.setCellValueFactory(new PropertyValueFactory<>("Completed"));

        buyerColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("cart_value"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        messageidColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        messageCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        messageContenColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        messageCarIdColumn.setCellValueFactory(new PropertyValueFactory<>("cartId"));
        productStypeC.setItems(FXCollections.observableArrayList(ProductSType.values()));
        productSexC.setItems(FXCollections.observableArrayList(Productsex.values()));
        productType.getItems().addAll(ProductType.values());
        setupCommentRatingField();

    }


    public void leaveComment() {
        tabPane.getSelectionModel().select(commentsTab);
    }

    public void loadTabValues() {
        if (productsTab.isSelected()) {
            loadProductListManager();
            List<Warehouse> record = genericHib.getAllRecords(Warehouse.class);
            warehouseComboBox.getItems().addAll(record);
        } else if (warehouseTab.isSelected()) {
            loadWarehouseList();
        }
        else if (commentsTab.isSelected()) {
            loadCommentList();
            loadProductListManager();
            List<Product> record = genericHib.getAllRecords(Product.class);
            productComboBox.getItems().addAll(record);
        }
        else if (usersTab.isSelected()) {
            loadUserData();
        }
        else if (ordersTab.isSelected()) {
            getAllCarts();
        }
        else if (messageTab.isSelected()) {
            getAllMessages();
        }
    }
    public void enableProductFields() {
        if (productType.getSelectionModel().getSelectedItem() == ProductType.SHOES) {
            productColorField.setDisable(false);
            productSizeField.setDisable(false);
            productStypeC.setDisable(false);
            productFeaturesField.setDisable(true);
            productSexC.setDisable(false);
            productMaterialField.setDisable(true);
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.LACES) {
            productColorField.setDisable(false);
            productSizeField.setDisable(false);
            productStypeC.setDisable(true);
            productFeaturesField.setDisable(true);
            productSexC.setDisable(true);
            productMaterialField.setDisable(true);
        } else {
            productColorField.setDisable(true);
            productSizeField.setDisable(false);
            productStypeC.setDisable(true);
            productFeaturesField.setDisable(false);
            productSexC.setDisable(true);
            productMaterialField.setDisable(false);
        }
    }

    //----------------------Product functionality-------------------------------//

    private void loadProductListManager() {
        productListManager.getItems().clear();
        productListManager.getItems().addAll(genericHib.getAllRecords(Product.class));
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void addNewProduct() {
        try {
            Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
            if (selectedWarehouse == null) {
                JavaFxCustomUtils.showAlert("Alert", "Warehouse was not Selected");
                return;
            }
            Warehouse warehouse = genericHib.getEntityById(Warehouse.class, selectedWarehouse.getId());

            if (productType.getSelectionModel().getSelectedItem() == ProductType.SHOES) {
                genericHib.create(new Shoes(productTitleField.getText(), productDescriptionField.getText(),
                        Double.parseDouble(productPriceField.getText()), productManufacturerField.getText(),
                        warehouse, productSizeField.getText(), productSexC.getValue(),
                        productColorField.getText(), productStypeC.getValue()));
            } else if (productType.getSelectionModel().getSelectedItem() == ProductType.INSOLES) {
                genericHib.create(new Insoles(productTitleField.getText(), productDescriptionField.getText(),
                        productManufacturerField.getText(), Double.parseDouble(productPriceField.getText()),
                        warehouse, productSizeField.getText(), productMaterialField.getText(),
                        productFeaturesField.getText()));
            } else if (productType.getSelectionModel().getSelectedItem() == ProductType.LACES) {
                genericHib.create(new Laces(productTitleField.getText(), productDescriptionField.getText(),
                        Double.parseDouble(productPriceField.getText()), productManufacturerField.getText(),
                        warehouse, productSizeField.getText(), productColorField.getText()));
            } else {
                JavaFxCustomUtils.showAlert("Product error", "Wrong product type");
                return;
            }

            loadProductListManager();
            loadData();
        } catch (Exception e) {
            JavaFxCustomUtils.showAlert("Error","Could not create product");
        }
    }




    public void updateProduct() {
        Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            selectedProduct.setTitle(productTitleField.getText());
            selectedProduct.setDescription(productDescriptionField.getText());
            selectedProduct.setManufacturer(productManufacturerField.getText());
            double price = Double.parseDouble(productPriceField.getText());
            selectedProduct.setPrice(price);

            if (selectedProduct instanceof Shoes) {
                Shoes shoes = (Shoes) selectedProduct;
                shoes.setSize(productSizeField.getText());
                shoes.setColor(productColorField.getText());
            } else if (selectedProduct instanceof Laces) {
                Laces laces = (Laces) selectedProduct;
                laces.setSize(productSizeField.getText());
                laces.setColor(productColorField.getText());
            } else if (selectedProduct instanceof Insoles) {
                Insoles insoles = (Insoles) selectedProduct;
                insoles.setSize(productSizeField.getText());
                insoles.setMaterial(productMaterialField.getText());
                insoles.setFeatures(productFeaturesField.getText());
            }

            customHib.update(selectedProduct);

            loadProductListManager();
            loadData();
        }
    }


    public void deleteProduct() {
        try {
            Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();

            if (selectedProduct == null) {
                JavaFxCustomUtils.showAlert("Deletion Error", "No product selected.");
                return;             }

            customHib.deleteProduct(selectedProduct.getId());
            loadProductListManager();
        } catch (Exception e) {
            JavaFxCustomUtils.showAlert("Deletion Error", "An error occurred while deleting the product: " + e.getMessage());

        }
    }




    public void loadProductData() {
        Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productTitleField.setText(selectedProduct.getTitle());
            productDescriptionField.setText(selectedProduct.getDescription());
            productPriceField.setText(String.valueOf(selectedProduct.getPrice()));
            productManufacturerField.setText(selectedProduct.getManufacturer());
            if (selectedProduct instanceof Shoes) {
                Shoes shoes = (Shoes) selectedProduct;
                productSizeField.setText(shoes.getSize());
                productColorField.setText(shoes.getColor());
            } else if (selectedProduct instanceof Laces) {
                Laces laces = (Laces) selectedProduct;
                productSizeField.setText(laces.getSize());
                productColorField.setText(laces.getColor());
            } else if (selectedProduct instanceof Insoles) {
                Insoles insoles = (Insoles) selectedProduct;
                productSizeField.setText(insoles.getSize());
                productMaterialField.setText(insoles.getMaterial());
                productFeaturesField.setText(insoles.getFeatures());
            }
        }
    }


//----------------------Warehouse functionality-----------------------------//

    private void loadWarehouseList() {
        warehouseList.getItems().clear();
        warehouseList.getItems().addAll(warehouseHib.getAllWarehouse());
    }

    public void addNewWarehouse() {
        Warehouse newWarehouse = new Warehouse(titleWarehouseField.getText(), addressWarehouseField.getText());
        warehouseHib.createWarehouse(newWarehouse);
        loadWarehouseList();
    }

    public void updateWarehouse() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        if (selectedWarehouse != null) {
            selectedWarehouse.setTitle(titleWarehouseField.getText());
            selectedWarehouse.setAddress(addressWarehouseField.getText());
            warehouseHib.updateWarehouse(selectedWarehouse);
            loadWarehouseList();
        } else {
            JavaFxCustomUtils.showAlert("No Selection", "Please select a warehouse to update.");
        }
    }

    public void removeWarehouse() {
        try {
            Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();

            if (selectedWarehouse == null) {
                JavaFxCustomUtils.showAlert("Error", "No warehouse selected.");
                return;
            }

            Warehouse warehouse = customHib.getEntityById(Warehouse.class, selectedWarehouse.getId());
            if (warehouse == null) {
                JavaFxCustomUtils.showAlert("Error", "Warehouse not found with ID: " + selectedWarehouse.getId());
                return;
            }

            customHib.delete(Warehouse.class, selectedWarehouse.getId());
            loadWarehouseList();
        } catch (Exception e) {
            JavaFxCustomUtils.showAlert("Error", "An unexpected error occurred.");
        }
    }

    public void loadWarehouseData() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        if (selectedWarehouse != null) {
            titleWarehouseField.setText(selectedWarehouse.getTitle());
            addressWarehouseField.setText(selectedWarehouse.getAddress());
        } else {
            titleWarehouseField.setText("");
            addressWarehouseField.setText("");
            JavaFxCustomUtils.showAlert("No Selection", "No warehouse selected.");
        }
    }


//----------------------Comment functionality-----------------------------//
public void addNewComment() {
    try {
        Product selectedProduct = productComboBox.getSelectionModel().getSelectedItem();
        Integer selectedRating = commentRatingField.getSelectionModel().getSelectedItem();

        if (selectedProduct != null && selectedRating != null) {
            Product product = genericHib.getEntityById(Product.class, selectedProduct.getId());


            LocalDate currentDate = LocalDate.now();


            Comment newComment = new Comment(
                    product,
                    product.getTitle(),
                    commentBodyField.getText(),
                    currentDate,
                    selectedRating
            );


            genericHib.create(newComment);


            commentBodyField.clear();
            commentRatingField.getSelectionModel().clearSelection();


            loadCommentList();
        } else {

            JavaFxCustomUtils.showAlert("Error", "You must select a product and a rating to leave a comment.");
        }
    } catch (Exception e) {

        JavaFxCustomUtils.showAlert("Error", "An unexpected error occurred while submitting your comment");
    }
}

    public void updateComment() {
        try {
            Comment selectedComment = commentList.getSelectionModel().getSelectedItem();
            if (selectedComment == null) {
                JavaFxCustomUtils.showAlert("No Selection", "No comment selected.");
                return;
            }

            Integer selectedRating = commentRatingField.getSelectionModel().getSelectedItem();
            if (selectedRating != null) {
                selectedComment.setRating(selectedRating);
            }

            String newCommentBody = commentBodyField.getText();
            if (newCommentBody != null && !newCommentBody.isEmpty()) {
                selectedComment.setCommentBody(newCommentBody);
            }

            genericHib.update(selectedComment);
            loadCommentList();

        } catch (Exception e) {
            JavaFxCustomUtils.showAlert("Error", "An error occurred while updating the comment");
        }
    }

    private void loadCommentList() {
        commentList.getItems().clear();
        commentList.getItems().addAll(genericHib.getAllRecords(Comment.class));
    }

    public void removeComment() {
        try {
            Comment selectedComment = commentList.getSelectionModel().getSelectedItem();

            if (selectedComment == null) {
                JavaFxCustomUtils.showAlert("No Selection", "Please select a comment in the list.");
                return;
            }
            genericHib.delete(Comment.class, selectedComment.getId());

            loadCommentList();

        } catch (IllegalArgumentException e) {
            JavaFxCustomUtils.showAlert("Error", "Comment doesnt exist");
        } catch (Exception e) {
            e.printStackTrace();
            JavaFxCustomUtils.showAlert("Error", "An error occurred while attempting to delete the comment");
        }
    }

    //----------------------USER functionality-----------------------------//


    public void loadUserData() {
        List<Manager> managers = userHib.getAllManagers();
        List<Customer> customers = userHib.getAllCustomers();

        managerTableView.getItems().setAll(managers);
        customerTableView.getItems().setAll(customers);
    }

    public void deleteUser() {
        try {
            User selectedUser = customerTableView.getSelectionModel().getSelectedItem();
            boolean isCustomerSelected = true;

            if (selectedUser == null) {
                selectedUser = managerTableView.getSelectionModel().getSelectedItem();
                isCustomerSelected = false;
            }

            if (selectedUser == null) {
                JavaFxCustomUtils.showAlert("No Selection", "Please select a user in the list.");
                return;
            }

            userHib.deleteUser(selectedUser.getId());

            if (isCustomerSelected) {
                loadUserData();
            } else {
                loadUserData();
            }

        } catch (IllegalArgumentException e) {
            JavaFxCustomUtils.showAlert("Error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JavaFxCustomUtils.showAlert("Error", "An error occurred while attempting to delete the user: " + e.getMessage());
        }

    }



    //----------------------Cart functionality-----------------------------//

    @FXML
    protected void addToCart() {
        Product selectedProduct = productList.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            currentOrder.getItems().add(selectedProduct);
            updateTotalAmount();

        } else {
            JavaFxCustomUtils.showAlert("No Selection", "No product selected. Please select a product to add.");
        }
    }

    @FXML
    protected void removeFromCart() {
        Product selectedProduct = currentOrder.getSelectionModel().getSelectedItem();


        if (selectedProduct != null) {

            currentOrder.getItems().remove(selectedProduct);
            updateTotalAmount();
        } else {
            JavaFxCustomUtils.showAlert("No Selection", "No product selected to remove from the cart.");
        }
    }

    private void updateTotalAmount() {
        double totalAmount = currentOrder.getItems().stream().mapToDouble(Product::getPrice).sum();
        totalAmountLabel.setText(String.format("%.2f", totalAmount));
    }


    public void addProductsToCart() {
        EntityManager em = null;
        try {
            em = genericHib.getEntityManager();
            em.getTransaction().begin();
            ObservableList<Product> productsInOrder = currentOrder.getItems();
            if (productsInOrder.isEmpty()) {
                JavaFxCustomUtils.showAlert("Error", "There are no products in the current order to add to the cart.");
                return;
            }
            Cart newCart = new Cart();
            newCart.setDateCreated(LocalDate.now());
            newCart.setUser(currentUser);
            newCart.setItemsInCart(new ArrayList<>());
            double totalValue = 0.0;
            for (Product product : productsInOrder) {
                Product managedProduct = em.merge(product);
                managedProduct.setCart(newCart);
                newCart.getItemsInCart().add(managedProduct);
                totalValue += managedProduct.getPrice();
            }

            newCart.setCart_value(totalValue);

            em.persist(newCart);

            em.getTransaction().commit();
            currentOrder.getItems().clear();

        } catch (Exception e) {

            if (em != null) {
                em.getTransaction().rollback();
            }
            JavaFxCustomUtils.showAlert("Error", "An unexpected error occurred while adding products to your cart: " + e.getMessage());
        } finally {
            // Close the EntityManager when done
            if (em != null) {
                em.close();
            }
        }
    }

    //----------------------Orders functionality-----------------------------//

    public void getAllCarts() {

        if (currentUser instanceof Customer) {
            List<Cart> customerCarts = genericHib.getCartsForUser(currentUser.getId());
            cartTable.getItems().setAll(customerCarts);
        } else {
            List<Cart> carts = genericHib.getAllRecords(Cart.class);
            cartTable.getItems().setAll(carts);
        }
    }

    public void deleteCart() {
        try {
            Cart selectedCart = cartTable.getSelectionModel().getSelectedItem();

            if (selectedCart == null) {
                JavaFxCustomUtils.showAlert("No Selection", "Please select a cart in the list.");
                return;
            }
            getAllCarts();
                    } catch (IllegalArgumentException e) {
            JavaFxCustomUtils.showAlert("Error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JavaFxCustomUtils.showAlert("Error", "An error occurred while attempting to delete the cart: " + e.getMessage());
        }
    }

    public void updateCart() {
        try {
            Cart selectedCart = cartTable.getSelectionModel().getSelectedItem();
            if (selectedCart == null) {
                JavaFxCustomUtils.showAlert("No Selection", "No cart selected. Please select a cart in the list.");
                return;
            }

            int newUserId = Integer.parseInt(CustomerIdField.getText());
            double newCartValue = Double.parseDouble(OrderValueField.getText());
            boolean newOrderCompleted = orderCompletedCheckBox.isSelected();
            User user = genericHib.getEntityById(User.class, newUserId);

            if (user != null) {
                selectedCart.setUser(user);
            } else {
                JavaFxCustomUtils.showAlert("Error", "No user found with the provided ID.");
                return;
            }

            selectedCart.setCart_value(newCartValue);
            selectedCart.setCompleted(newOrderCompleted);

            genericHib.update(selectedCart);

            getAllCarts();

        } catch (NumberFormatException e) {
            JavaFxCustomUtils.showAlert("Invalid Data", "Please enter valid numerical values for user ID and cart value.");
        } catch (Exception e) {
            JavaFxCustomUtils.showAlert("Error", "An error occurred while updating the cart: " + e.getMessage());
        }
    }

    //----------------------Statistics functionality-----------------------------//


    public void filterProjects() {
        double minCost = Double.parseDouble(minCostField.getText());
        double maxCost = Double.parseDouble(maxCostField.getText());
        int userId = Integer.parseInt(userIdField.getText());
        LocalDate fromDate = fromField.getValue();
        LocalDate toDate = toField.getValue();

        List<Cart> filteredData = genericHib.filterData(minCost,maxCost, userId, fromDate, toDate);

        cartData.getItems().clear();
        cartData.getItems().addAll(filteredData);
        double sumOfValues = filteredData.stream()
                .mapToDouble(Cart::getCart_value)
                .sum();

        priceView.getItems().clear();
        priceView.getItems().add("Total Cart Value: " + String.format("%.2f", sumOfValues));

        Map<LocalDate, Double> summedValuesByDate = filteredData.stream()
                .collect(Collectors.groupingBy(
                        Cart::getDateCreated,
                        Collectors.summingDouble(Cart::getCart_value)));

        cartValuePieChart.getData().clear();

        // Creating PieChart.Data for each date and its total value
        summedValuesByDate.forEach((date, sum) -> {
            PieChart.Data slice = new PieChart.Data(date.toString(), sum);
            cartValuePieChart.getData().add(slice);
        });
    }

    //----------------------Message functionality-----------------------------//

    public void sendMessage() {
        try {
            Cart selectedCart = cartTable.getSelectionModel().getSelectedItem();
            if (selectedCart == null) {
                JavaFxCustomUtils.showAlert("No Selection", "No cart selected. Please select a cart in the list.");
                return;
            }
            String messageContent = messageInputField.getText();

            if (messageContent != null && !messageContent.trim().isEmpty()) {
                Message newMessage = new Message(messageContent, selectedCart);

                genericHib.create(newMessage);
                messageInputField.clear();
            } else {
                JavaFxCustomUtils.showAlert("Error", "You must enter a message to send.");
            }
        } catch (Exception e) {
            JavaFxCustomUtils.showAlert("Error", "An unexpected error occurred while sending your message: " + e.getMessage());
        }
    }

    public void messageResponse() {
        try {
            Message selectedMessage = messageTable.getSelectionModel().getSelectedItem();

            if (selectedMessage == null) {
                JavaFxCustomUtils.showAlert("No Selection", "No message selected. Please select a message in the list.");
                return;
            }
            Cart cart = selectedMessage.getCart();
            String responseContent = messageResponseField.getText();

            if (responseContent != null && !responseContent.trim().isEmpty()) {
                Message responseMessage = new Message(responseContent, cart);
                genericHib.create(responseMessage);

                messageResponseField.clear();

            } else {
                JavaFxCustomUtils.showAlert("Error", "You must enter a message to send.");
            }
        } catch (Exception e) {
            JavaFxCustomUtils.showAlert("Error", "An unexpected error occurred while sending your response: " + e.getMessage());
        }
        getAllMessages();
    }

    public void deleteMessage() {
        try {
            Message selectedMessage = messageTable.getSelectionModel().getSelectedItem();

            if (selectedMessage == null) {
                JavaFxCustomUtils.showAlert("No Selection", "No message selected. Please select a message in the list.");
                return;
            }

        } catch (Exception e) {
            JavaFxCustomUtils.showAlert("Error", "An unexpected error occurred while deleting the message");
        }
        getAllMessages();
    }

    public void getAllMessages(){
        List<Message> messages = genericHib.getAllRecords(Message.class);
        messageTable.getItems().setAll(messages);
    }

}
