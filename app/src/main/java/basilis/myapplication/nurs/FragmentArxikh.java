package basilis.myapplication.nurs;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentArxikh.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentArxikh#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentArxikh extends Fragment {
    private ImageSwitcher imageSwitcher;
    final int[] images=new int[]{R.drawable.image1, R.drawable.image2, R.drawable.image3};
    private Handler imageSwitchHandler;
    private boolean firstImage;
    private boolean secondImage;
    int messageCount=images.length;
    // to keep current Index of ImageID array
    int currentIndex=-1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentArxikh() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentArxikh.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentArxikh newInstance(String param1, String param2) {
        FragmentArxikh fragment = new FragmentArxikh();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.arxikh, container, false);
        imageSwitcher = (ImageSwitcher) view.findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });
        // Declare the animations and initialize them
        Animation in = AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_out);
        // set the animation type to imageSwitcher
        imageSwitcher.setInAnimation(in);
        imageSwitcher.setOutAnimation(out);
        imageSwitcher.setImageResource(images[0]);
        // NOTE : We are calling the onFragmentInteraction() declared in the MainActivity
        // ie we are sending "Fragment 1" as title parameter when fragment1 is activated
        if (mListener != null) {
            mListener.onFragmentInteraction("Αρχική");
        }
        imageSwitchHandler = new Handler();
        imageSwitchHandler.post(runnableCode);
        return view;
    }

    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            if(firstImage)
            {
                imageSwitcher.setImageResource(R.drawable.image1);
                firstImage=false;
            }else if(!firstImage&&secondImage)
            {
                imageSwitcher.setImageResource(R.drawable.image2);
                firstImage=true;
                secondImage=false;
            }else
            {
                imageSwitcher.setImageResource(R.drawable.image3);
                secondImage=true;
            }
            imageSwitchHandler.postDelayed(this, 3000);
        }
    };
    @Override
    public void onStop() {
        imageSwitchHandler.removeCallbacks(runnableCode);
        super.onStop();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String title) {
        if (mListener != null) {
            mListener.onFragmentInteraction(title);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }
}
