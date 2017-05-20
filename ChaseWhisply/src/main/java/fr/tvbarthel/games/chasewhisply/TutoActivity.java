package fr.tvbarthel.games.chasewhisply;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import fr.tvbarthel.games.chasewhisply.ui.TutoFragment;

public class TutoActivity extends FragmentActivity implements ViewSwitcher.ViewFactory {
	public static final String EXTRA_HELP_REQUESTED = "ExtraHelpRequested";
	public static final int NB_PAGES = 11;
	private static final String FIRST_LAUNCH_KEY = "First_launch_KEY";
	private SharedPreferences mPrefs;
	private String[] mPageTitles;
	private TextSwitcher mTitleSwitcher;
	private int mLastPosition;
	private Animation mSlideLeftInAnimation;
	private Animation mSlideLeftOutAnimation;
	private Animation mSlideRightInAnimation;
	private Animation mSlideRightOutAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tuto);
		mLastPosition = 0;

		//load animation
		mSlideLeftInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
		mSlideLeftOutAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left_out);
		mSlideRightInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
		mSlideRightOutAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);

		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		final boolean firstLaunch = mPrefs.getBoolean(FIRST_LAUNCH_KEY, true);
		final boolean helpRequested = getIntent().getBooleanExtra(EXTRA_HELP_REQUESTED, false);
		if (!firstLaunch && !helpRequested) {
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			finish();
		}

		mPageTitles = new String[]{
				getResources().getString(R.string.tuto_title_page_0),
				getResources().getString(R.string.tuto_title_page_1),
				getResources().getString(R.string.tuto_title_page_2),
				getResources().getString(R.string.tuto_title_page_3),
				getResources().getString(R.string.tuto_title_page_4),
				getResources().getString(R.string.tuto_title_page_5),
				getResources().getString(R.string.tuto_title_page_6),
				getResources().getString(R.string.tuto_title_page_7),
				getResources().getString(R.string.tuto_title_page_8),
				getResources().getString(R.string.tuto_title_page_9),
				getResources().getString(R.string.tuto_title_page_10)};

		//initialize title text switcher
		mTitleSwitcher = (TextSwitcher) findViewById(R.id.tuto_text_switcher);
		mTitleSwitcher.setFactory(this);
		mTitleSwitcher.setCurrentText(getResources().getString(R.string.tuto_title_page_0));

		final ViewPager pager = (ViewPager) findViewById(R.id.pager);
		final TutoPagerAdapter adapter = new TutoPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(adapter.getCount());
		pager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.tuto_page_margin));
		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i2) {
			}

			@Override
			public void onPageSelected(int newPosition) {
				if (newPosition > mLastPosition) {
					mTitleSwitcher.setInAnimation(mSlideLeftInAnimation);
					mTitleSwitcher.setOutAnimation(mSlideLeftOutAnimation);
				} else {
					mTitleSwitcher.setInAnimation(mSlideRightInAnimation);
					mTitleSwitcher.setOutAnimation(mSlideRightOutAnimation);
				}
				mTitleSwitcher.setText(adapter.getPageTitle(newPosition));
				mLastPosition = newPosition;
			}

			@Override
			public void onPageScrollStateChanged(int i) {
			}
		});

		final Button closeButton = (Button) findViewById(R.id.closeButton);
		closeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				closeTutorial();
			}
		});
	}

	private void closeTutorial() {
		final boolean helpRequested = getIntent().getBooleanExtra(EXTRA_HELP_REQUESTED, false);
		if (!helpRequested) {
			final Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			final SharedPreferences.Editor editor = mPrefs.edit();
			editor.putBoolean(FIRST_LAUNCH_KEY, false);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				editor.apply();
			} else {
				editor.commit();
			}
		}
		finish();
	}

	@Override
	public View makeView() {
		TextView textView = new TextView(this);
		textView.setTextAppearance(this, android.R.style.TextAppearance_Holo_Large);
		textView.setTextColor(getResources().getColor(R.color.holo_dark_green));
		textView.setGravity(Gravity.CENTER);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		textView.setLayoutParams(layoutParams);
		return textView;
	}


	private class TutoPagerAdapter extends FragmentPagerAdapter {
		public TutoPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			final int layoutResId;
			switch (position) {
				case 0:
					layoutResId = R.layout.fragment_tuto_welcome;
					break;
				case 1:
					layoutResId = R.layout.fragment_tuto_target;
					break;
				case 2:
					layoutResId = R.layout.fragment_tuto_target_bis;
					break;
				case 3:
					layoutResId = R.layout.fragment_tuto_combo;
					break;
				case 4:
					layoutResId = R.layout.fragment_tuto_aim_tips;
					break;
				case 5:
					layoutResId = R.layout.fragment_tuto_ammo;
					break;
				case 6:
					layoutResId = R.layout.fragment_tuto_ammo_bis;
					break;
				case 7:
					layoutResId = R.layout.fragment_tuto_play_button;
					break;
				case 8:
					layoutResId = R.layout.fragment_tuto_leaderboard_button;
					break;
				case 9:
					layoutResId = R.layout.fragment_tuto_achievement_button;
					break;
				case 10:
					layoutResId = R.layout.fragment_tuto_ready_to_fight;
					break;
				default:
					layoutResId = R.layout.fragment_tuto_default_page;
					break;
			}
			return TutoFragment.newInstance(layoutResId);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mPageTitles[position];
		}

		@Override
		public int getCount() {
			return NB_PAGES;
		}
	}
}
