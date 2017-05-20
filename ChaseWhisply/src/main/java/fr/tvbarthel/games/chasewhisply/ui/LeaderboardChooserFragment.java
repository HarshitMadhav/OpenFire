package fr.tvbarthel.games.chasewhisply.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.tvbarthel.games.chasewhisply.R;
import fr.tvbarthel.games.chasewhisply.model.GameModeFactory;

public class LeaderboardChooserFragment extends Fragment implements View.OnClickListener {

	private GameModeView mGameMode1;
	private GameModeView mGameMode2;
	private GameModeView mGameMode3;
	private GameModeView mGameMode4;
	private Listener mListener;

	public interface Listener {
		public void onLeaderboardChosen(int leaderboardStringId);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof GameScoreFragment.Listener) {
			mListener = (LeaderboardChooserFragment.Listener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet LeaderboardChooserFragment.Listener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_leaderboard_chooser, container, false);

		//First mode: Kill as many Ghosts as you can in 30 seconds. (level 1)
		mGameMode1 = (GameModeView) v.findViewById(R.id.leaderboard_chooser_mode1);
		mGameMode1.setModelForLeaderboard(GameModeFactory.createRemainingTimeGame(1));
		mGameMode1.setGameModeSelectedListener(this);

		//Second mode: Kill as many Ghosts as you can in 60 seconds. (level 2)
		mGameMode2 = (GameModeView) v.findViewById(R.id.leaderboard_chooser_mode2);
		mGameMode2.setModelForLeaderboard(GameModeFactory.createRemainingTimeGame(2));
		mGameMode2.setGameModeSelectedListener(this);

		//Third mode: Kill as many Ghosts as you can in 30 seconds. (level 3)
		mGameMode3 = (GameModeView) v.findViewById(R.id.leaderboard_chooser_mode3);
		mGameMode3.setModelForLeaderboard(GameModeFactory.createRemainingTimeGame(3));
		mGameMode3.setGameModeSelectedListener(this);

		//Fourth mode: survival
		mGameMode4 = (GameModeView) v.findViewById(R.id.leaderboard_chooser_mode4);
		mGameMode4.setModelForLeaderboard(GameModeFactory.createSurvivalGame(1));
		mGameMode4.setGameModeSelectedListener(this);

		return v;
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.leaderboard_chooser_mode1:
			case R.id.leaderboard_chooser_mode2:
			case R.id.leaderboard_chooser_mode3:
			case R.id.leaderboard_chooser_mode4:
				final int leaderboardStringId = ((GameModeView) view).getModel().getLeaderboardStringId();
				mListener.onLeaderboardChosen(leaderboardStringId);
				break;
		}

	}
}