package com.exercise.caraugmentedreality.View.Fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.exercise.caraugmentedreality.Contract.EngineGuideContract;
import com.exercise.caraugmentedreality.Presenter.EngineGuidePresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;

import butterknife.BindView;

public class EngineGuideFragment extends BaseFragment implements EngineGuideContract.View {

    EngineGuidePresenter mPresenter;

    @BindView(R.id.bt_back)
    ImageButton bt_back;

    @BindView(R.id.iv_1) ImageView iv_1;
    @BindView(R.id.iv_2) ImageView iv_2;
    @BindView(R.id.iv_3) ImageView iv_3;
    @BindView(R.id.iv_4) ImageView iv_4;
    @BindView(R.id.iv_5) ImageView iv_5;
    @BindView(R.id.iv_6) ImageView iv_6;
    @BindView(R.id.iv_7) ImageView iv_7;
    @BindView(R.id.iv_8) ImageView iv_8;

    @BindView(R.id.tv_1) TextView tv_1;
    @BindView(R.id.tv_2) TextView tv_2;
    @BindView(R.id.tv_3) TextView tv_3;
    @BindView(R.id.tv_4) TextView tv_4;
    @BindView(R.id.tv_5) TextView tv_5;
    @BindView(R.id.tv_6) TextView tv_6;
    @BindView(R.id.tv_7) TextView tv_7;
    @BindView(R.id.tv_8) TextView tv_8;

    @BindView(R.id.tv_details1) TextView tv_details1;
    @BindView(R.id.tv_details2) TextView tv_details2;
    @BindView(R.id.tv_details3) TextView tv_details3;
    @BindView(R.id.tv_details4) TextView tv_details4;
    @BindView(R.id.tv_details5) TextView tv_details5;
    @BindView(R.id.tv_details6) TextView tv_details6;
    @BindView(R.id.tv_details7) TextView tv_details7;
    @BindView(R.id.tv_details8) TextView tv_details8;

    MediaPlayer mp1,mp2,mp3,mp4,mp5,mp6,mp7,mp8;

    public EngineGuideFragment(){
        mPresenter = new EngineGuidePresenter();
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_engine_guide,container,false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState == null){
            mp1 = MediaPlayer.create(getActivity(), R.raw.eng1);
            mp2 = MediaPlayer.create(getActivity(), R.raw.eng2);
            mp3 = MediaPlayer.create(getActivity(), R.raw.eng3);
            mp4 = MediaPlayer.create(getActivity(), R.raw.eng4);
            mp5 = MediaPlayer.create(getActivity(), R.raw.eng5);
            mp6 = MediaPlayer.create(getActivity(), R.raw.eng6);
            mp7 = MediaPlayer.create(getActivity(), R.raw.eng7);
            mp8 = MediaPlayer.create(getActivity(), R.raw.eng8);

            bt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHomeScreen();
                }
            });

            iv_1.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                if(mp1.isPlaying()){
                    iv_1.setImageResource(R.drawable.ic_speaker);
                    mp1.pause();
                }else{
                    iv_1.setImageResource(R.drawable.ic_speakerfilled);
                    mp1.start();
                }
            }});
            iv_2.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                if(mp2.isPlaying()){
                    iv_2.setImageResource(R.drawable.ic_speaker);
                    mp2.pause();
                }else{
                    iv_2.setImageResource(R.drawable.ic_speakerfilled);
                    mp2.start();
                }
            }});
            iv_3.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                if(mp3.isPlaying()){
                    iv_3.setImageResource(R.drawable.ic_speaker);
                    mp3.pause();
                }else{
                    iv_3.setImageResource(R.drawable.ic_speakerfilled);
                    mp3.start();
                }
            }});
            iv_4.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                if(mp4.isPlaying()){
                    iv_4.setImageResource(R.drawable.ic_speaker);
                    mp4.pause();
                }else{
                    iv_4.setImageResource(R.drawable.ic_speakerfilled);
                    mp4.start();
                }  }});
            iv_5.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                if(mp5.isPlaying()){
                    iv_5.setImageResource(R.drawable.ic_speaker);
                    mp5.pause();
                }else{
                    iv_5.setImageResource(R.drawable.ic_speakerfilled);
                    mp5.start();
                }  }});
            iv_6.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                if(mp6.isPlaying()){
                    iv_6.setImageResource(R.drawable.ic_speaker);
                    mp6.pause();
                }else{
                    iv_6.setImageResource(R.drawable.ic_speakerfilled);
                    mp6.start();
                }  }});
            iv_7.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                if(mp7.isPlaying()){
                    iv_7.setImageResource(R.drawable.ic_speaker);
                    mp7.pause();
                }else{
                    iv_7.setImageResource(R.drawable.ic_speakerfilled);
                    mp7.start();
                }  }});
            iv_8.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                if(mp8.isPlaying()){
                    iv_8.setImageResource(R.drawable.ic_speaker);
                    mp8.pause();
                }else{
                    iv_8.setImageResource(R.drawable.ic_speakerfilled);
                    mp8.start();
                }  }});

            tv_1.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { tv_details1.setVisibility(View.VISIBLE); }});
            tv_2.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { tv_details2.setVisibility(View.VISIBLE); }});
            tv_3.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { tv_details3.setVisibility(View.VISIBLE); }});
            tv_4.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { tv_details4.setVisibility(View.VISIBLE); }});
            tv_5.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { tv_details5.setVisibility(View.VISIBLE); }});
            tv_6.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { tv_details6.setVisibility(View.VISIBLE); }});
            tv_7.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { tv_details7.setVisibility(View.VISIBLE); }});
            tv_8.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { tv_details8.setVisibility(View.VISIBLE); }});

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mp1 != null && mp2 != null && mp3 != null&& mp4 != null&& mp5 != null&& mp6 != null&& mp7 != null
                && mp8 != null) {
            mp1.pause();mp7.pause();
            mp2.pause();mp8.pause();
            mp3.pause();
            mp4.pause();
            mp5.pause();
            mp6.pause();
            if (getActivity().isFinishing()) {
                mp1.stop();mp2.stop();mp3.stop();mp4.stop();mp5.stop();mp6.stop();
                mp7.stop();mp8.stop();
                mp1.release();mp2.release();mp3.release();mp4.release();mp5.release();mp6.release();
                mp7.release();mp8.release();
            }
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showMessage(String message) {
        showToastMessage(message);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showError(int error) {

    }

    @Override
    public void moveToHomeScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        onPause();
        getActivity().finish();
        startActivity(intent);
    }
}