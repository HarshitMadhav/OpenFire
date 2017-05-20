package fr.tvbarthel.games.chasewhisply.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.tvbarthel.games.chasewhisply.R;
import fr.tvbarthel.games.chasewhisply.model.GameMode;

public class GameModeView extends RelativeLayout {

	private final TextView mGameModeDescription;
	private final ImageView mGameModeImage;
	private GameMode mModel;

	public GameModeView(Context context) {
		this(context, null);
	}

	public GameModeView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setBackgroundResource(R.drawable.card_shadow);
		setClickable(true);

		final LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.view_game_mode, this, true);

		mGameModeImage = (ImageView) getChildAt(0);
		mGameModeDescription = (TextView) getChildAt(1);
	}

	/**
	 * get GameMode
	 *
	 * @return mModel
	 */
	public GameMode getModel() {
		return mModel;
	}

	/**
	 * set model to the view to configure rules and image
	 *
	 * @param model GameMode
	 */
	public void setModel(GameMode model) {
		mModel = model;
		mGameModeImage.setImageResource(mModel.getImage());
		mGameModeDescription.setText(mModel.getRules());
	}

	public void setModelForLeaderboard(GameMode model) {
		mModel = model;
		mGameModeImage.setImageResource(mModel.getImage());
		mGameModeDescription.setText(mModel.getLeaderboardDescriptionStringId());
	}

	/**
	 * set a listener for game mode selection
	 *
	 * @param listener
	 */
	public void setGameModeSelectedListener(OnClickListener listener) {
		this.setOnClickListener(listener);
	}

	public void setGameModeEnabled(boolean isAllowed) {
		mGameModeImage.setEnabled(isAllowed);
	}
}
