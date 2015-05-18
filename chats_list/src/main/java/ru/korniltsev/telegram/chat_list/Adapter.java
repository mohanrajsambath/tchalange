package ru.korniltsev.telegram.chat_list;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.drinkless.td.libcore.telegram.TdApi;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import ru.korniltsev.telegram.core.Utils;
import ru.korniltsev.telegram.core.recycler.BaseAdapter;
import ru.korniltsev.telegram.core.views.AvatarView;
import rx.functions.Action1;

public class Adapter extends BaseAdapter<TdApi.Chat, Adapter.VH> {
    public static final DateTimeFormatter MESSAGE_TIME_FORMAT = DateTimeFormat.forPattern("K:mm a");
    public static final ColorStateList COLOR_SYSTEM = ColorStateList.valueOf(0xff6b9cc2);

    private final Context ctx;
    private final int myId;
    private final Action1<TdApi.Chat> clicker;
    private ColorStateList COLOR_TEXT = ColorStateList.valueOf(0xff8a8a8a);

    public Adapter(Context ctx, int myId, Action1<TdApi.Chat> clicker) {
        super(ctx);
        this.ctx = ctx;
        this.myId = myId;
        this.clicker = clicker;
        setHasStableIds(true);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = getViewFactory().inflate(R.layout.item_chat, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        TdApi.Chat chat = getItem(position);
        TdApi.MessageContent message = chat.topMessage.message;

        if (chat.type instanceof TdApi.PrivateChatInfo){
            //name
            TdApi.User u = ((TdApi.PrivateChatInfo) chat.type).user;
            holder.name.setText(Utils.uiName(u));
            //group_icon
            holder.iconGroupChat.setVisibility(View.GONE);
        } else {
            //name
            TdApi.GroupChatInfo group = (TdApi.GroupChatInfo) chat.type;
            holder.name.setText(group.groupChat.title);
            //group_icon
            holder.iconGroupChat.setVisibility(View.VISIBLE);
        }

        long timeInMillis = Utils.dateToMillis(chat.topMessage.date);
        long local = DateTimeZone.UTC.convertUTCToLocal(timeInMillis);
        holder.time.setText(MESSAGE_TIME_FORMAT.print(local));

        if (message instanceof TdApi.MessageText) {
            TdApi.MessageText text = (TdApi.MessageText) message;
            holder.message.setText(text.textWithSmilesAndUserRefs);
            holder.message.setTextColor(COLOR_TEXT);
        } else {
            holder.message.setText("system");
            holder.message.setTextColor(COLOR_SYSTEM);
        }
        if (chat.unreadCount > 0){
            holder.iconBottom.setVisibility(View.VISIBLE);
            holder.iconBottom.setBackgroundResource(R.drawable.ic_badge);
            holder.iconBottom.setText(String.valueOf(chat.unreadCount));
        } else {
            holder.iconBottom.setVisibility(View.GONE);
        }

        if (chat.topMessage.fromId == myId){
            if (chat.lastReadOutboxMessageId == chat.topMessage.id){
                holder.iconTop.setVisibility(View.GONE);
            } else {
                holder.iconTop.setBackgroundResource(R.drawable.ic_unread);
                holder.iconTop.setVisibility(View.VISIBLE);
            }
        } else {
            holder.iconTop.setVisibility(View.GONE);
        }
        loadAvatar(holder, chat);
    }

    private void loadAvatar(VH holder, TdApi.Chat chat) {
        holder.avatar.loadAvatarFor(chat);
    }


    @Override
    public long getItemId(int position) {
        return getItem(position).id;
    }

    class VH extends RecyclerView.ViewHolder {
        final AvatarView avatar;
        final TextView message;
        private final TextView name;
        private final TextView time;
        private final ImageView iconTop;
        private final TextView iconBottom;
        private final ImageView iconGroupChat;

        public VH(View itemView) {
            super(itemView);
            avatar = (AvatarView) itemView.findViewById(R.id.avatar);
            message = (TextView) itemView.findViewById(R.id.message);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            iconTop = (ImageView) itemView.findViewById(R.id.icon_top);
            iconBottom = (TextView) itemView.findViewById(R.id.icon_bottom);
            iconGroupChat = (ImageView) itemView.findViewById(R.id.group_chat_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clicker.call(getItem(getPosition()));
                }
            });


        }
    }


}
