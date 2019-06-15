package fr.ensicaen.oware.client.controllers;

import fr.ensicaen.oware.client.OwareApp;
import fr.ensicaen.oware.client.stages.OwareStage;
import lombok.Setter;

/**
 * Represents an abstract controller for the Oware application.
 * This class is used to control a stage and link it with the main application.
 *
 * @author Maxime Malgorn <maxime.malgorn@ecole.ensicaen.fr>
 * @author Pierre Poulain <pierre.poulain@ecole.ensicaen.fr>
 */
@Setter
public abstract class Controller {

	/**
	 * The custom stage which uses this controller
	 */
	OwareStage stage;

	/**
	 * The Oware application
	 */
	OwareApp application;

	/**
	 * Called when the controller is initializing from the stage.
	 */
	public abstract void initialize();

}
