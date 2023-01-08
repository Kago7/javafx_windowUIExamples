/* Name: Karl Gonsalves
 * Date: OCT. 15, 2021
 * Course: ICS4U1
 * Program Description:  This program emulates a pizza ordering experience at Little Caesars. The user first chooses their
 * pizza size, their toppings, and their number of specific drinks. The cost of their choices is represented in the textfields below them.
 * The user will then calculate the subtotal of their order-but it wont work if the user has not selected a pizza size. If they have, the final
 * cost textfields will update with the relevant prices for subtotal, delivery fee-the box changes green if free delivery, the hst tax, and the grand total
 * The checkout button will recite the order choices the user previously made and ask to confirm their order, if yes the user is greeted with alert and if no 
 * the user goes back to main screen and picks up from before. The clear button clears all changing fields and sets them to default. The variables
 * that store these choices in the background are also reset to default. Finally, the exit button confirms the choice and closes the program after showing
 * goodbye alert.
 */

package application;

//import classes
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LittleCaesarsMain extends Application {

	// global vars
	TextField[] costTextfields;
	int numToppings = 0;
	int numDrinks = 0, numDrinks1 = 0, numDrinks2 = 0, numDrinks3 = 0, numDrinks4 = 0;
	double numDrinksPrice = 0;
	static DecimalFormat df;
	ToggleGroup sizebuttons;
	TextField[] finalCostTextfields;
	double subtotal = 0, delivery = 0, hst = 0, gtotal = 0;
	Button checkout;
	String orderSummary = "";
	CheckBox[] checkboxes;
	ComboBox[] cboxDrinkNum;
	ImageView icon;

	public void start(Stage primaryStage) {
		try {

			// setup gridpane and scene
			GridPane root = new GridPane();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Little Caesars");
			root.setHgap(20);
			root.setVgap(10);
			root.setPadding(new Insets(15, 20, 15, 20));

			/*****************************************************************************/

			// set window event for close x button
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent e) {
					// create the two possible alerts
					Alert exit = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES,
							ButtonType.NO);
					exit.setTitle("Little Caesars");
					exit.setHeaderText(null);
					Alert thank = new Alert(AlertType.INFORMATION, "Thank you for choosing Little Caesars!",
							ButtonType.OK);
					thank.setTitle("Little Caesars");
					thank.setHeaderText(null);

					// check if user presses yes or no on first confirm alert
					Optional<ButtonType> result = exit.showAndWait();
					if (result.get() == ButtonType.YES) {
						thank.showAndWait();
						System.exit(0);
					}

					// check if user presses no/yes. if yes close program
					// info. if no, return main screen
					else if (result.get() == ButtonType.NO) {
						e.consume();
					}
				}
			});

			/*****************************************************************************/

			// create Imageview for title, logo, and alert image.
			ImageView title = new ImageView(new Image("file:logo.png"));
			icon = new ImageView(new Image("file:icon.png"));
			ImageView visa = new ImageView(new Image("file:visa.png"));

			/****************************************************************************************/

			// create size titled pane stuff

			RadioButton sizeSmall = new RadioButton("Small"); // these are all the radiobuttons for the sizes
			sizeSmall.setAlignment(Pos.TOP_LEFT);
			sizeSmall.setOnAction(e -> sizeSelected(sizeSmall));
			sizeSmall.setUserData("- Small");

			RadioButton sizeMedium = new RadioButton("Medium");
			sizeMedium.setAlignment(Pos.TOP_LEFT);
			sizeMedium.setOnAction(e -> sizeSelected(sizeMedium));
			sizeMedium.setUserData("- Medium");

			RadioButton sizeLarge = new RadioButton("Large");
			sizeLarge.setAlignment(Pos.TOP_LEFT);
			sizeLarge.setOnAction(e -> sizeSelected(sizeLarge));
			sizeLarge.setUserData("- Large");

			RadioButton sizeParty = new RadioButton("Party");
			sizeParty.setAlignment(Pos.TOP_LEFT);
			sizeParty.setOnAction(e -> sizeSelected(sizeParty));
			sizeParty.setUserData("- Party");

			sizebuttons = new ToggleGroup(); // toggle group for all size radiobuttons.
			sizebuttons.getToggles().addAll(sizeSmall, sizeMedium, sizeLarge, sizeParty);

			VBox vbSizes = new VBox(15); // this is the vbox containing the sizes radiobuttons
			vbSizes.getChildren().addAll(sizeSmall, sizeMedium, sizeLarge, sizeParty);
			vbSizes.setAlignment(Pos.CENTER_LEFT);

			TitledPane titlepSize = new TitledPane(); // this is the titled pane containing the tilepane and
														// radiobuttons
			titlepSize.setText("SIZE");
			titlepSize.setFont(Font.font("System", FontWeight.BOLD, 12));
			titlepSize.setCollapsible(false);
			titlepSize.setContent(vbSizes);
			titlepSize.setPrefWidth(125);
			titlepSize.setPrefHeight(185);

			/******************************************************************************/

			// setup the toppings titled pane

			CheckBox toppingMushrooms = new CheckBox("Mushrooms"); // these ares the checkboxes for toppings
			toppingMushrooms.setPrefWidth(125);
			toppingMushrooms.setOnAction(e -> toppingsSelected(toppingMushrooms));

			CheckBox toppingGreenPeppers = new CheckBox("Green Peppers");
			toppingGreenPeppers.setPrefWidth(125);
			toppingGreenPeppers.setOnAction(e -> toppingsSelected(toppingGreenPeppers));

			CheckBox toppingOnions = new CheckBox("Onions");
			toppingOnions.setPrefWidth(125);
			toppingOnions.setOnAction(e -> toppingsSelected(toppingOnions));

			CheckBox toppingHotPeppers = new CheckBox("Hot Peppers");
			toppingHotPeppers.setPrefWidth(125);
			toppingHotPeppers.setOnAction(e -> toppingsSelected(toppingHotPeppers));

			CheckBox toppingPepperoni = new CheckBox("Pepperoni");
			toppingPepperoni.setPrefWidth(125);
			toppingPepperoni.setOnAction(e -> toppingsSelected(toppingPepperoni));

			CheckBox toppingBacon = new CheckBox("Bacon");
			toppingBacon.setPrefWidth(125);
			toppingBacon.setOnAction(e -> toppingsSelected(toppingBacon));

			CheckBox toppingTomatoes = new CheckBox("Tomatoes");
			toppingTomatoes.setPrefWidth(125);
			toppingTomatoes.setOnAction(e -> toppingsSelected(toppingTomatoes));

			CheckBox toppingExtraCheese = new CheckBox("Extra Cheese");
			toppingExtraCheese.setPrefWidth(125);
			toppingExtraCheese.setOnAction(e -> toppingsSelected(toppingExtraCheese));

			// manually add all these toppings to an array because i forgot to do the for
			// loop first
			checkboxes = new CheckBox[8];
			checkboxes[0] = toppingMushrooms;
			checkboxes[1] = toppingGreenPeppers;
			checkboxes[2] = toppingOnions;
			checkboxes[3] = toppingHotPeppers;
			checkboxes[4] = toppingPepperoni;
			checkboxes[5] = toppingBacon;
			checkboxes[6] = toppingTomatoes;
			checkboxes[7] = toppingExtraCheese;

			TilePane tilepToppings = new TilePane();
			tilepToppings.getChildren().addAll(toppingMushrooms, toppingGreenPeppers, toppingOnions, toppingHotPeppers,
					toppingPepperoni, toppingBacon, toppingTomatoes, toppingExtraCheese);
			tilepToppings.setAlignment(Pos.CENTER_LEFT);
			tilepToppings.setPrefColumns(2);
			tilepToppings.setPrefRows(4);
			tilepToppings.setHgap(15);
			tilepToppings.setVgap(15);
			tilepToppings.setOrientation(Orientation.VERTICAL);

			TitledPane titlepToppings = new TitledPane(); // this is the titled pane containing the tilepane and
															// radiobuttons
			titlepToppings.setText("TOPPINGS");
			titlepToppings.setFont(Font.font("System", FontWeight.BOLD, 12));
			titlepToppings.setCollapsible(false);
			titlepToppings.setContent(tilepToppings);
			titlepToppings.setAlignment(Pos.TOP_LEFT);
			titlepToppings.setPrefHeight(185);

			/******************************************************************************/

			// setup beverages titles pane

			Label coke = new Label("Coke"); // these are the labels and comboboxes
			coke.setPrefWidth(70);
			Label sprite = new Label("Sprite");
			sprite.setPrefWidth(70);
			Label orange = new Label("Orange");
			orange.setPrefWidth(70);
			Label rootBeer = new Label("Root Beer");
			rootBeer.setPrefWidth(70);

			cboxDrinkNum = new ComboBox[4]; // this combobox array sets up for instances of it.
			for (int a = 0; a < 4; a++) {
				int b = a;
				cboxDrinkNum[a] = new ComboBox();
				cboxDrinkNum[a].getItems().addAll(0, 1, 2, 3, 4, 5, 6);
				cboxDrinkNum[a].setPromptText("0");
				cboxDrinkNum[a].setPrefWidth(70);
				cboxDrinkNum[a].setOnAction(e -> drinkSelect(cboxDrinkNum));
			}

			// setting user data for names of drinks
			cboxDrinkNum[0].setUserData("Coke");
			cboxDrinkNum[1].setUserData("Sprite");
			cboxDrinkNum[2].setUserData("Orange");
			cboxDrinkNum[3].setUserData("Root Beer");

			TilePane tilepBev = new TilePane(); // this tile pane contains the labels/comboboxes
			tilepBev.getChildren().addAll(coke, sprite, orange, rootBeer, cboxDrinkNum[0], cboxDrinkNum[1],
					cboxDrinkNum[2], cboxDrinkNum[3]);
			tilepBev.setAlignment(Pos.CENTER_LEFT);
			tilepBev.setPrefColumns(2);
			tilepBev.setHgap(10);
			tilepBev.setVgap(10);
			tilepBev.setOrientation(Orientation.VERTICAL);

			TitledPane titlepBev = new TitledPane(); // this is the titled pane containing the tilepane and radiobuttons
			titlepBev.setText("BEVERAGES");
			titlepBev.setFont(Font.font("System", FontWeight.BOLD, 12));
			titlepBev.setCollapsible(false);
			titlepBev.setContent(tilepBev);
			titlepBev.setAlignment(Pos.TOP_LEFT);
			titlepBev.setPrefHeight(185);

			/****************************************************************/

			// setup label for 3 free toppings
			Label free = new Label("First three (3) toppings are free!");
			free.setFont(Font.font("System", FontWeight.BOLD, 12));
			free.setAlignment(Pos.CENTER);
			GridPane.setHalignment(free, HPos.CENTER);

			/****************************************************************/

			// setup three textfields for cost of size,toppings,drinks
			costTextfields = new TextField[3];
			for (int a = 0; a < 3; a++) {
				costTextfields[a] = new TextField("$0.00");
				costTextfields[a].setEditable(false);
				costTextfields[a].setMaxWidth(100);
				costTextfields[a].setAlignment(Pos.CENTER);
				costTextfields[a].setFont(Font.font("System", FontWeight.BOLD, 12));
				GridPane.setHalignment(costTextfields[a], HPos.CENTER);
			}

			/****************************************************************/

			// setup total costs textfields

			Label subTotal = new Label("SUBTOTAL:"); // these are the labels
			subTotal.setPrefWidth(90);
			Label deliveryFee = new Label("DELIVERY FEE:");
			deliveryFee.setPrefWidth(90);
			Label hst = new Label("HST:");
			hst.setPrefWidth(90);
			Label gTotal = new Label("GRAND TOTAL:");
			gTotal.setPrefWidth(90);

			finalCostTextfields = new TextField[4]; // setup the 4 textfields
			for (int a = 0; a < 4; a++) {
				finalCostTextfields[a] = new TextField();
				finalCostTextfields[a].setEditable(false);
				finalCostTextfields[a].setMaxWidth(100);
				finalCostTextfields[a].setAlignment(Pos.CENTER);
				finalCostTextfields[a].setFont(Font.font("System", FontWeight.NORMAL, 12));
				GridPane.setHalignment(finalCostTextfields[a], HPos.CENTER);
			}

			TilePane tilepFinalCosts = new TilePane(); // this tile pane contains the labels/textfields
			tilepFinalCosts.getChildren().addAll(subTotal, deliveryFee, hst, gTotal, finalCostTextfields[0],
					finalCostTextfields[1], finalCostTextfields[2], finalCostTextfields[3]);
			tilepFinalCosts.setAlignment(Pos.CENTER);
			tilepFinalCosts.setPrefColumns(2);
			tilepFinalCosts.setHgap(10);
			tilepFinalCosts.setVgap(10);
			tilepFinalCosts.setOrientation(Orientation.VERTICAL);

			/********************************************************************/

			// setup cost / control buttons

			Button calc = new Button("CALCULATE"); // setup the 4 different buttons here
			calc.setPrefWidth(100);
			calc.setOnAction(e -> doCalc());

			Button clear = new Button("CLEAR");
			clear.setPrefWidth(100);
			clear.setOnAction(e -> clearClick());

			checkout = new Button("CHECKOUT");
			checkout.setPrefWidth(100);
			checkout.setDisable(true);
			checkout.setOnAction(e -> checkoutClick());

			Button exit = new Button("EXIT");
			exit.setPrefWidth(100);
			exit.setOnAction(e -> exit());

			VBox vbCalcs = new VBox(15); // this is the vbox containing the cost/calc buttons
			vbCalcs.getChildren().addAll(calc, clear, checkout, exit);
			vbCalcs.setAlignment(Pos.CENTER);

			/***************************************************************************************/

			// get the children
			root.add(title, 0, 0);
			root.setColumnSpan(title, 3);
			GridPane.setHalignment(title, HPos.CENTER);

			root.add(titlepSize, 0, 1);

			root.add(titlepToppings, 1, 1);

			root.add(titlepBev, 2, 1);

			root.add(free, 1, 2);

			for (int a = 0; a < 3; a++) {
				root.add(costTextfields[a], a, 3);
			}

			root.add(visa, 0, 4);
			GridPane.setHalignment(visa, HPos.CENTER);

			root.add(tilepFinalCosts, 1, 4);

			root.add(vbCalcs, 2, 4);

			// show the stage after setting up
			root.setGridLinesVisible(false);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// this method exits the program, showing the relevant series of alerts
	public void exit() {

		// create possible alerts
		Alert exit = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
		exit.setTitle("Little Caesars");
		exit.setHeaderText(null);
		Alert thank = new Alert(AlertType.INFORMATION, "Thank you for choosing Little Caesars!", ButtonType.OK);
		thank.setTitle("Little Caesars");
		thank.setHeaderText(null);

		// check if user presses yes or no on first confirm alert
		Optional<ButtonType> result = exit.showAndWait();
		if (result.get() == ButtonType.YES) {
			thank.showAndWait();
			System.exit(0);
		}
	}

	// this method clears all fields to original and resets any values to 0
	public void clearClick() {

		// uncheck size buttons and cost textfield
		sizebuttons.selectToggle(null);
		costTextfields[0].setText("$0.00");

		// uncheck all topiings and cost textfield and numtoppings int
		for (int a = 0; a < 8; a++) {
			checkboxes[a].setSelected(false);
		}
		numToppings = 0;
		costTextfields[1].setText("$0.00");

		// unselect all combo boxes and cost textfield and related vars
		numDrinks = 0;
		numDrinksPrice = 0;
		for (int a = 0; a < 4; a++) {
			cboxDrinkNum[a].getSelectionModel().selectFirst();
		}
		costTextfields[2].setText("$0.00");

		// set final costs textfields to blank and related vars and change color to
		// white
		for (int a = 0; a < 4; a++) {
			finalCostTextfields[a].setText("");
		}
		subtotal = 0;
		delivery = 0;
		hst = 0;
		gtotal = 0;
		finalCostTextfields[1].setStyle("-fx-control-inner-background: white");

		// finally disable the checkout button again
		checkout.setDisable(true);

	}

	// this method handles what to do when the checkout button is pressed. this
	// includes order summary and relevant
	// exit alerts.
	public void checkoutClick() {
		// create the alert for order summary
		Alert summary = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
		summary.setHeaderText(null);
		summary.setTitle("Order Summary");

		// setup final string for the order summary toppings and beverages
		orderSummary += "\n\nTOPPINGS:\n";

		for (int a = 0; a < 8; a++) {
			if (checkboxes[a].isSelected()) {
				orderSummary += "- ";
				orderSummary += checkboxes[a].getText();
				orderSummary += "\n";
			}
		}

		orderSummary += "\n\nBEVERAGES:\n";

		for (int a = 0; a < 4; a++) {
			if (cboxDrinkNum[a].getValue() != null && !cboxDrinkNum[a].getValue().equals("0")) { // check comboboxes for
																									// value if not null
																									// or 0
				if ((int) cboxDrinkNum[a].getValue() != 0) { // print beverages if you dont select 0 in combo box
					orderSummary += cboxDrinkNum[a].getValue();
					orderSummary += "x ";
					orderSummary += cboxDrinkNum[a].getUserData().toString();
					orderSummary += "\n";
				}
			}
		}

		// set the alert content text from final string (pizza size done manually)
		summary.setContentText("Is this order correct?\n\n\nPIZZA:\n"
				+ sizebuttons.getSelectedToggle().getUserData().toString() + orderSummary);

		Optional<ButtonType> result = summary.showAndWait();

		// if yes button proceed, if not go back to main screen and reset ordersummary
		// string
		if (result.get() == ButtonType.YES) {
			Alert bye = new Alert(AlertType.INFORMATION,
					"Thank you for ordering from Little Caesars!\nYour pizza will be delivered in 30 minutes or it's free!",
					ButtonType.OK);
			bye.setTitle("Little Caesars");
			bye.setHeaderText(null);
			bye.setGraphic(icon);
			bye.showAndWait();
			System.exit(0);
		} else {
			orderSummary = "";
		}

	}

	// this method calculates the prices that go in the final costs textfields. this
	// calculates the price based on the
	// items selected preceding the click action. Thus, changing items after
	// calculating and checking out will scam little caesar.
	public void doCalc() {

		// first check if the user has selected a pizza size and throw alert
		Alert noPizza = new Alert(AlertType.ERROR, "Your order could not be completed!\nPlease select a pizza size.",
				ButtonType.OK);
		noPizza.setHeaderText(null);
		noPizza.setTitle("Incomplete order");

		if (sizebuttons.getSelectedToggle() == null) { // throw alert if no pizza
			noPizza.showAndWait();
		} else { // if pizza size selected set final costs in text fields

			// calc subtotal and set it
			subtotal = Double.parseDouble(costTextfields[0].getText().substring(1))
					+ Double.parseDouble(costTextfields[1].getText().substring(1))
					+ Double.parseDouble(costTextfields[2].getText().substring(1));
			finalCostTextfields[0].setText("$" + df.format(subtotal));

			// calc delivery fee and set text field and color
			if (subtotal < 15) {
				delivery = 5.00;
				finalCostTextfields[1].setText("$" + df.format(delivery));
				finalCostTextfields[1].setStyle("-fx-control-inner-background: white");
			} else {
				delivery = 0.00;
				finalCostTextfields[1].setText("$0.00");
				finalCostTextfields[1].setStyle("-fx-control-inner-background: green");
			}

			// calc hst tax
			hst = subtotal * 0.13;
			finalCostTextfields[2].setText("$" + df.format(hst));

			// calc grand total
			gtotal = subtotal + hst + delivery;
			finalCostTextfields[3].setText("$" + df.format(gtotal));

			// enable checkout button
			checkout.setDisable(false);
		}

	}

	// method to keep count of drinks and change price according to what user
	// selects (number of drinks)
	public void drinkSelect(ComboBox[] combo) {

		// store num of drinks if combo box is not null and 0
		if (combo[0].getValue() != null && !combo[0].getValue().equals("0")) { // check combobox 1
			numDrinks1 = (int) combo[0].getValue();
		}
		if (combo[1].getValue() != null && !combo[1].getValue().equals("0")) { // check combobox 2
			numDrinks2 = (int) combo[1].getValue();
		}
		if (combo[2].getValue() != null && !combo[2].getValue().equals("0")) { // check combobox 3
			numDrinks3 = (int) combo[2].getValue();
		}
		if (combo[3].getValue() != null && !combo[3].getValue().equals("0")) { // check combobox 4
			numDrinks4 = (int) combo[3].getValue();
		}
		numDrinks = numDrinks1 + numDrinks2 + numDrinks3 + numDrinks4;

		if (numDrinks < 1) { // set textfield to 0 if all comboboxes are 0 or null
			costTextfields[2].setText("$0.00");

		} else if (numDrinks == 1) { // calculate price for textfield if equal to one drink
			costTextfields[2].setText("$0.99");
		} else { // calculate price for textfield if not greater than 1
			numDrinksPrice = 0.99 * numDrinks;
			costTextfields[2].setText("$" + df.format(numDrinksPrice));
		}

	}

	// this method handles what to do when toppings are selected. Changes price in
	// relevant textfield according to number.
	public void toppingsSelected(CheckBox cb) {

		// increment or decrement based on if topping is selected or not
		if (cb.isSelected()) {
			numToppings++;
		} else if (!cb.isSelected()) {
			numToppings--;
		}

		// set textfield price if more than 3 toppings
		if (numToppings > 3) {
			costTextfields[1].setText("$" + Integer.toString(numToppings - 3) + ".00");
		} else {
			costTextfields[1].setText("$0.00");
		}

	}

	// this method handles what to do when the sizes radiobuttons are pressed.
	// changes price in related textfield
	// according to the size.
	public void sizeSelected(RadioButton rd) {
		if (rd.getText().equals("Small")) {
			costTextfields[0].setText("$7.99");
		} else if (rd.getText().equals("Medium")) {
			costTextfields[0].setText("$8.99");
		} else if (rd.getText().equals("Large")) {
			costTextfields[0].setText("$9.99");
		} else if (rd.getText().equals("Party")) {
			costTextfields[0].setText("$10.99");
		}

	}

	// main method
	public static void main(String[] args) {

		// setup constructors for required classes
		df = new DecimalFormat("#.00");

		launch(args);
	}
}
