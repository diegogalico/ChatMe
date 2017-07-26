package com.schibsted.chatme.presentation.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.schibsted.chatme.R;
import com.schibsted.chatme.data.MessageEntity;
import com.schibsted.chatme.presentation.app.ChatmeApplication;
import com.schibsted.chatme.presentation.presenters.MessagePresenter;
import com.schibsted.chatme.presentation.presenters.impl.MessagePresenterImpl;
import com.schibsted.chatme.presentation.ui.adapters.MessageAdapter;
import com.schibsted.chatme.presentation.ui.utils.ScreenUtils;
import com.schibsted.chatme.presentation.ui.utils.TimeUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by diego.galico
 */

public class MessageFragment extends BaseFragment implements MessagePresenter.MessageView {

    public interface NavigationHandler {
        void navigateToLoginActivity();
    }

    private MessageFragment.NavigationHandler mNavigationHandler;

    private Unbinder mUnbinder;
    private LinearLayoutManager mLinearLayout;
    private MessageAdapter mMessageAdapter;

    @Inject
    MessagePresenterImpl mMessagePresenter;

    @Inject
    SharedPreferences mSharedPreferences;

    @BindView(R.id.recycler_view_messages)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar_messages)
    View mProgressBar;

    @BindView(R.id.edit_text_send_message)
    EditText mSendMessageEditText;

    public static MessageFragment newInstance() {
        MessageFragment messageFragment = new MessageFragment();
        return messageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initializeDependencyInjector();
    }

    private void initializeDependencyInjector() {
        ChatmeApplication app = (ChatmeApplication) getActivity().getApplication();
        app.getNetworkComponent().inject(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mLinearLayout = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayout);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mNavigationHandler = (MessageFragment.NavigationHandler) getActivity();
        } catch (ClassCastException ex) {
            throw new ClassCastException(getActivity().toString() + " must implement navigateToLoginActivity");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMessagePresenter.attachView(this);
        getActivity().setTitle(mMessagePresenter.getLoggedUser());
    }

    @Override
    public void onStart() {
        super.onStart();
        mMessagePresenter.getMessages();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mMessagePresenter.detachView();
    }

    @OnClick(R.id.button_send_message)
    public void sendMessageClick() {
        mMessageAdapter.appendMessage(createNewMessage());
        mSendMessageEditText.getText().clear();
        mRecyclerView.scrollToPosition(mMessageAdapter.getListMessages().size() - 1);
        View view = this.getView();
        if (view != null) {
            ScreenUtils.hideKeyboard(view, getContext());
        }
    }

    private MessageEntity createNewMessage(){
        MessageEntity message = new MessageEntity();
        message.setContent(mSendMessageEditText.getText().toString());
        message.setTime(TimeUtils.getCurrentTime());
        message.setMyMessage(true);
        return message;
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(Object message) {
    }

    @Override
    public void showMessages(List<MessageEntity> listMessages) {
        mMessageAdapter = new MessageAdapter(listMessages);
        mRecyclerView.setAdapter(mMessageAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                mMessagePresenter.removeLoggedUser();
                mNavigationHandler.navigateToLoginActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
