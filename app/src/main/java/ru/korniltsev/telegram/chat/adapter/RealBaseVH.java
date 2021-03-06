package ru.korniltsev.telegram.chat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.drinkless.td.libcore.telegram.TdApi;
import ru.korniltsev.telegram.core.rx.RxChat;
import ru.korniltsev.telegram.core.rx.UserHolder;
import ru.korniltsev.telegram.common.AppUtils;

import static junit.framework.Assert.assertTrue;

public abstract class RealBaseVH extends RecyclerView.ViewHolder {
    public final Adapter adapter;
    public RealBaseVH(View itemView, Adapter adapter) {
        super(itemView);
        this.adapter = adapter;
    }

    public abstract void bind(RxChat.ChatListItem item, long lastReadOutbox);

    public String getNameForSenderOf(TdApi.Message item) {
        return sGetNameForSenderOf(adapter.getUserHolder(), item);
//        int fromId = item.fromId;
//        assertTrue(fromId != 0);
//        TdApi.User user = adapter.getUserHolder().getUser(fromId);
//        return Utils.uiName(user);
    }

    public static String sGetNameForSenderOf(UserHolder uh, TdApi.Message item) {
        int fromId = item.fromId;
        assertTrue(fromId != 0);
        TdApi.User user = uh.getUser(fromId);
        return AppUtils.uiName(user, uh.getContext());
    }


}
