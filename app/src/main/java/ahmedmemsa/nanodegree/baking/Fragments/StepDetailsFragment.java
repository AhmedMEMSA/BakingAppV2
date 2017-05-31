package ahmedmemsa.nanodegree.baking.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import ahmedmemsa.nanodegree.baking.Pojo.Step;
import ahmedmemsa.nanodegree.baking.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment {
    private static final String ARG_STEPS = "step";

    private Step mStep;

    @BindView(R.id.step_video_view)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.step_description)
    TextView mDescription;

    private SimpleExoPlayer mExoPlayer;

    public StepDetailsFragment() {
    }

    public static StepDetailsFragment newInstance(Step step) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEPS, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStep = getArguments().getParcelable(ARG_STEPS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);

        ButterKnife.bind(this, rootView);
        if (mStep.getVideoURL() != null && !mStep.getVideoURL().equals("")) {
            initializePlayer(Uri.parse(mStep.getVideoURL()));
            mPlayerView.setContentDescription(mStep.getShortDescription());
        } else if (mStep.getThumbnailURL() != null && !mStep.getThumbnailURL().equals("")) {
            initializePlayer(Uri.parse(mStep.getThumbnailURL()));
            mPlayerView.setContentDescription(mStep.getShortDescription());
        } else {
            mPlayerView.setVisibility(View.GONE);
        }
        mDescription.setText(mStep.getDescription());
        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                    Util.getUserAgent(getActivity(), "Baking"), bandwidthMeter);

            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, dataSourceFactory, new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }


    }


    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }


    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


}
