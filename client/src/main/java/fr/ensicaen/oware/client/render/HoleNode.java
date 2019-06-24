package fr.ensicaen.oware.client.render;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

public class HoleNode {

    private static final Image SPRITE_SEEDS = new Image(HoleNode.class.getResourceAsStream("/images/sprite_seeds.png"));

    private static final Image SPRITE_HOLES = new Image(HoleNode.class.getResourceAsStream("/images/sprite_holes.png"));

    private static final int SPRITE_WIDTH = 75;

    private static final int SPRITE_HEIGHT = 72;

    private static final int SPRITE_NUMBER = 6;

    @Getter
    private Group group;

    private ImageView hole;

    private ImageView seeds;

    private Text text;

    @Getter
    @Setter
    private boolean disabled;

    public HoleNode(int x, int y) {
        this(x, y, false);
    }

    public HoleNode(int x, int y, boolean opponent) {
        this.group = new Group();
        this.hole = new ImageView();
        this.seeds = new ImageView();
        this.text = new Text();

        this.hole.setImage(SPRITE_HOLES);
        this.hole.setFitWidth(SPRITE_WIDTH);
        this.hole.setFitHeight(SPRITE_HEIGHT);
        this.hole.setViewport(new Rectangle2D(opponent ? 150 : 0, 0, SPRITE_WIDTH, SPRITE_HEIGHT));

        if (!opponent) {
            this.group.setOnMouseEntered(this::onMouseEntered);
            this.group.setOnMouseExited(this::onMouseExited);
        }

        this.seeds.setImage(SPRITE_SEEDS);
        this.seeds.setFitWidth(SPRITE_WIDTH);
        this.seeds.setFitHeight(SPRITE_HEIGHT);
        this.seeds.setViewport(new Rectangle2D(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT));

        this.text.setLayoutX(45);
        this.text.setLayoutY(65);
        this.text.setWrappingWidth(30);
        this.text.setStyle("-fx-font-size:18px;-fx-font-weight:bold;-fx-text-alignment:center");

        this.group.setLayoutX(x);
        this.group.setLayoutY(y);
        this.group.getChildren().addAll(this.hole, this.seeds, this.text);
    }

    public void update(int seeds, boolean disabled) {
        if (seeds < 0) {
            return;
        }

        int nb = Math.min(seeds, SPRITE_NUMBER);

        this.disabled = disabled || seeds == 0;

        this.seeds.setViewport(new Rectangle2D(nb * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
        this.text.setText(String.valueOf(seeds));
    }

    private void onMouseEntered(MouseEvent event) {
        this.hole.setViewport(new Rectangle2D(SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
    }

    private void onMouseExited(MouseEvent event) {
        this.hole.setViewport(new Rectangle2D(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
    }

}
