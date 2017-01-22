package com.plusend.diycode.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.base.Presenter;

public abstract class BaseFragment extends Fragment {
  private static final String TAG = "BaseFragment";
  private OnFragmentInteractionListener mListener;

  public BaseFragment() {
    // Required empty public constructor
  }

  protected abstract Presenter getPresenter();

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      // TODO
      //throw new RuntimeException(
      //    context.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  @Override public void onDetach() {

    super.onDetach();
    mListener = null;
  }

  @Override public void onStart() {
    super.onStart();
    if (getPresenter() != null) {
      getPresenter().start();
    } else {
      Log.d(TAG, "onStart getPresenter() == null");
    }
  }

  @Override public void onStop() {
    if (getPresenter() != null) {
      getPresenter().stop();
    } else {
      Log.d(TAG, "onStop getPresenter() == null");
    }
    super.onStop();
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri uri);
  }

  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }
}
