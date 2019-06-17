package fr.ensicaen.oware.client.stages;

import javafx.stage.StageStyle;

/**
 * Represents the Menu stage.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
public class MenuStage extends OwareStage {

	/**
	 * {@inheritDoc}
	 */
	public MenuStage() {
		super("menu.fxml", "Oware | Connect to a server");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setup() {
		this.initStyle(StageStyle.TRANSPARENT);
		this.setResizable(false);
		this.getScene().setFill(null);
	}

}
