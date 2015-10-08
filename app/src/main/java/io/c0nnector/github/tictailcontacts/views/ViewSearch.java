package io.c0nnector.github.tictailcontacts.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.transitionseverywhere.TransitionManager;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.util.Strings;
import io.c0nnector.github.tictailcontacts.util.UtilView;
import io.c0nnector.github.tictailcontacts.util.Val;
import rx.Observable;
import rx.functions.Func1;

/**
 * Contacts search view
 */
public class ViewSearch extends BaseRelativeLayout {

    RelativeLayout rootView;

    ViewSearchListener listener;

    @Bind(R.id.vSearch)
    View vSearch;

    @Bind(R.id.vSearchWrapper)
    View vWrapper;

    @Bind(R.id.txtSearch)
    TextView txtSearch;

    @Bind(R.id.btnClose)
    View btnClose;

    @OnClick(R.id.btnClose)
    public void onBtnClose(){

        //hide view only when there's no text
        if (Strings.isBlank(txtSearch.getText().toString())) {
            listener.onSearchClose();
            hide();
        }

        else clear();
    }

    @OnTextChanged(R.id.txtSearch)
    public void onTextChanged(CharSequence text){
        listener.onTextSearch(text.toString().toLowerCase());
    }

    public ViewSearch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Bind Searchview to another view
     * @param rootView root view, used to animate changes
     */
    public void bind(RelativeLayout rootView, ViewSearchListener listener){
        this.rootView = rootView;
        this.listener = listener;
    }


    public void show(){
        animateChange();
        UtilView.show(vWrapper);
    }

    public void hide(){

        animateChange();
        UtilView.show(vWrapper, false);
    }

    public void clear(){
        txtSearch.setText("");
    }


    private void animateChange(){
        if (Val.notNull(rootView)) TransitionManager.beginDelayedTransition(rootView);
    }

    public interface ViewSearchListener{
        void onTextSearch(String query);
        void onSearchClose();
    }
}
