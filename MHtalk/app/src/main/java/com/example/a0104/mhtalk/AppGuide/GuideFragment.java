package com.example.a0104.mhtalk.AppGuide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a0104.mhtalk.MainActivity;
import com.example.a0104.mhtalk.R;

/**
 * Created by a0104 on 2017-07-04.
 */

public class GuideFragment extends Fragment {

    int pageno;
    String[] guide_text = {"안녕하세요.\n\n문희가 만든\n우리 가족을 위한 \n메신저 어플리케이션입니다.\n",
            "일정 관리\n메세지\n위치 확인이\n가능합니다.",
    "주의!\n셋만 쓰려고 만들었기 때문에\n오류가 많을 수 있습니다.\n"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.guide_fragment, container, false);
        TextView guide = (TextView) v.findViewById(R.id.guide_tv);
        Button start_btn = (Button) v.findViewById(R.id.guide_btn);

        if (pageno!=2) start_btn.setVisibility(View.INVISIBLE);

        guide.setText(guide_text[pageno]);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), MainActivity.class);
                startActivity(in);
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageno = getArguments().getInt("idx");
        }
    }


}
