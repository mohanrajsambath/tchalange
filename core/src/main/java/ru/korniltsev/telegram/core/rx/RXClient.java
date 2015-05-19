package ru.korniltsev.telegram.core.rx;

import android.content.Context;
import android.util.Log;
import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TG;
import org.drinkless.td.libcore.telegram.TdApi;
import org.drinkless.td.libcore.telegram.TdApi.TLObject;
import ru.korniltsev.telegram.core.adapters.RequestHandlerAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import javax.inject.Inject;
import javax.inject.Singleton;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by korniltsev on 21/04/15.
 */
@Singleton
public class RXClient {

    public static final Func1<TLObject, TdApi.Messages> CAST_TO_MESSAGE = new Func1<TLObject, TdApi.Messages>() {
        @Override
        public TdApi.Messages call(TLObject o) {
            return (TdApi.Messages) o;
        }
    };
    public static final Func1<TLObject, TdApi.User> CAST_TO_USER = new Func1<TLObject, TdApi.User>() {
        @Override
        public TdApi.User call(TLObject o) {
            return (TdApi.User) o;
        }
    };
    public static final Func1<TLObject, TdApi.Chats> CAST_TO_CHATS = new Func1<TLObject, TdApi.Chats>() {
        @Override
        public TdApi.Chats call(TLObject o) {
            return (TdApi.Chats) o;
        }
    };
    public static final Func1<TLObject, TdApi.UpdateFile> CAST_TO_FILE_UPDATE = new Func1<TLObject, TdApi.UpdateFile>() {
        @Override
        public TdApi.UpdateFile call(TLObject o) {
            return (TdApi.UpdateFile) o;
        }
    };
    public static final Func1<TLObject, Boolean> ONLY_FILE_UPDATES = new Func1<TLObject, Boolean>() {
        @Override
        public Boolean call(TLObject tlObject) {
            return tlObject instanceof TdApi.UpdateFile;
        }
    };
    public static final Func1<TLObject, Boolean> ONLY_NEW_MESSAGE_UPDATES = new Func1<TLObject, Boolean>() {
        @Override
        public Boolean call(TLObject tlObject) {
            return tlObject instanceof TdApi.UpdateNewMessage;
        }
    };
    public static final Func1<TLObject, TdApi.UpdateNewMessage> CAST_TO_NEW_MESSAGE_UPDATE = new Func1<TLObject, TdApi.UpdateNewMessage>() {
        @Override
        public TdApi.UpdateNewMessage call(TLObject tlObject) {
            return (TdApi.UpdateNewMessage) tlObject;
        }
    };
    public static final Func1<TLObject, Boolean> ONLY_UPDATE_MESSAGE_ID = new Func1<TLObject, Boolean>() {
        @Override
        public Boolean call(TLObject tlObject) {
            return tlObject instanceof TdApi.UpdateMessageId;
        }
    };
    public static final Func1<TLObject, TdApi.UpdateMessageId> CAST_TO_UPDATE_MESSAGE_ID = new Func1<TLObject, TdApi.UpdateMessageId>() {
        @Override
        public TdApi.UpdateMessageId call(TLObject tlObject) {
            return (TdApi.UpdateMessageId) tlObject;
        }
    };
    public static final Func1<TLObject, Boolean> ONLY_DELETE_MESSAGES = new Func1<TLObject, Boolean>() {
        @Override
        public Boolean call(TLObject tlObject) {
            return tlObject instanceof TdApi.UpdateDeleteMessages;
        }
    };
    public static final Func1<TLObject, TdApi.UpdateDeleteMessages> CAST_TO_DELETE_MESSAGES = new Func1<TLObject, TdApi.UpdateDeleteMessages>() {
        @Override
        public TdApi.UpdateDeleteMessages call(TLObject tlObject) {
            return (TdApi.UpdateDeleteMessages) tlObject;
        }
    };
    public static final Func1<TLObject, Boolean> ONLY_UPDATE_MESSAGE_DATE = new Func1<TLObject, Boolean>() {
        @Override
        public Boolean call(TLObject tlObject) {
            return tlObject instanceof TdApi.UpdateMessageDate;
        }
    };
    public static final Func1<TLObject, TdApi.UpdateMessageDate> CAST_TO_UPDATE_MESSAGE_DATE = new Func1<TLObject, TdApi.UpdateMessageDate>() {
        @Override
        public TdApi.UpdateMessageDate call(TLObject tlObject) {
            return (TdApi.UpdateMessageDate) tlObject;
        }
    };
    public static final Func1<TLObject, Boolean> ONLY_UPDATE_CHAT_READ_INBOX = new Func1<TLObject, Boolean>() {
        @Override
        public Boolean call(TLObject tlObject) {
            return tlObject instanceof TdApi.UpdateChatReadInbox;
        }
    };
    public static final Func1<TLObject, TdApi.UpdateChatReadInbox> CAST_UPDATE_CHAT_READ_INBOX = new Func1<TLObject, TdApi.UpdateChatReadInbox>() {
        @Override
        public TdApi.UpdateChatReadInbox call(TLObject tlObject) {
            return (TdApi.UpdateChatReadInbox) tlObject;
        }
    };

    public static final Func1<TLObject, Boolean> ONLY_UPDATE_CHAT_READ_OUTBOX = new Func1<TLObject, Boolean>() {
        @Override
        public Boolean call(TLObject tlObject) {
            return tlObject instanceof TdApi.UpdateChatReadOutbox;
        }
    };
    public static final Func1<TLObject, TdApi.UpdateChatReadOutbox> CAST_UPDATE_CHAT_READ_OUTBOX = new Func1<TLObject, TdApi.UpdateChatReadOutbox>() {
        @Override
        public TdApi.UpdateChatReadOutbox call(TLObject tlObject) {
            return (TdApi.UpdateChatReadOutbox) tlObject;
        }
    };
    public static final Func1<TLObject, TdApi.UpdateMessageContent> CAST_TO_UPDATE_MESSAGE_CONTENT = new Func1<TLObject, TdApi.UpdateMessageContent>() {
        @Override
        public TdApi.UpdateMessageContent call(TLObject tlObject) {
            return (TdApi.UpdateMessageContent) tlObject;
        }
    };
    public static final Func1<TLObject, Boolean> ONLY_UPDATE_MESSAGE_CONTENT = new Func1<TLObject, Boolean>() {
        @Override
        public Boolean call(TLObject tlObject) {
            return tlObject instanceof TdApi.UpdateMessageContent;
        }
    };
    public static final String OPTION_CONNECTION_STATE = "connection_state";
    public static final Func1<TdApi.UpdateOption, Boolean> ONLY_CONNECTED_STATE_OPTION = new Func1<TdApi.UpdateOption, Boolean>() {
        @Override
        public Boolean call(TdApi.UpdateOption updateOption) {
            return updateOption.value instanceof TdApi.OptionString
                && updateOption.name.equals(OPTION_CONNECTION_STATE);
        }
    };
    public static final Func1<TLObject, Boolean> ONLY_UPDATE_OPTION = new Func1<TLObject, Boolean>() {
        @Override
        public Boolean call(TLObject tlObject) {
            return tlObject instanceof TdApi.UpdateOption;
        }
    };
    public static final Func1<TLObject, TdApi.UpdateOption> CAST_TO_UPDATE_OBJECT = new Func1<TLObject, TdApi.UpdateOption>() {
        @Override
        public TdApi.UpdateOption call(TLObject tlObject) {
            return (TdApi.UpdateOption) tlObject;
        }
    };
    public static final Func1<TLObject, Boolean> PREDICATE = new Func1<TLObject, Boolean>() {
        @Override
        public Boolean call(TLObject tlObject) {
            return tlObject instanceof TdApi.UpdateNotificationSettings;
        }
    };
    public static final Func1<TLObject, TdApi.UpdateNotificationSettings> FUNC = new Func1<TLObject, TdApi.UpdateNotificationSettings>() {
        @Override
        public TdApi.UpdateNotificationSettings call(TLObject tlObject) {
            return (TdApi.UpdateNotificationSettings) tlObject;
        }
    };
    private Context ctx;

    private final Client client;
    private final PublishSubject<TdApi.TLObject> globalSubject2 = PublishSubject.create();
    private final PublishSubject<TdApi.UpdateFile> fileUpdates = PublishSubject.create();
    private final PublishSubject<TdApi.UpdateFileProgress> fileProgressUpdates = PublishSubject.create();
    private final BehaviorSubject<TdApi.UpdateOption> connectedState = BehaviorSubject.create(new TdApi.UpdateOption(OPTION_CONNECTION_STATE, new TdApi.OptionBoolean(false)));
    private Observable<TLObject> globalObservableWithBackPressure;

    @Inject
    public RXClient(Context ctx) {
        this.ctx = ctx;
        globalObservableWithBackPressure = globalSubject2.onBackpressureBuffer();
        TG.setUpdatesHandler(new Client.ResultHandler() {
            @Override
            public void onResult(TLObject object) {
                if (object instanceof TdApi.UpdateFileProgress){
                    fileProgressUpdates.onNext((TdApi.UpdateFileProgress) object);
                } else if (object instanceof TdApi.UpdateFile){
//                    try {
                        TdApi.UpdateFile o = (TdApi.UpdateFile) object;
                        fileUpdates.onNext(o);
//                        Log.e("DownloadFile", "\tfinish : " + coolTagForFileId(o.fileId) + " " + o.path);
//                    } catch (Exception e) {
//                        Log.e("RxClientError", "error: ", e);
//                    }
                }
                globalSubject2.onNext(object);
            }
        });
        TG.setDir(ctx.getFilesDir().getAbsolutePath() + "/");

        globalObservableWithBackPressure.filter(ONLY_UPDATE_OPTION)
                .map(CAST_TO_UPDATE_OBJECT)
                .filter(ONLY_CONNECTED_STATE_OPTION)
                .subscribe(connectedState)
        ;

//        globalSubject
//                .subscribe(new Action1<TLObject>() {
//                    @Override
//                    public void call(TLObject tlObject) {
//                        Log.e("Update", "probably unhandled update\n" + tlObject);
//                    }
//                });

        this.client = TG.getClientInstance();
    }

    public Observable<TdApi.UpdateOption> getConnectedState() {
        return connectedState;
    }

    //observe function on ui thread
    public Observable<TLObject> sendCachedRXUI(final TdApi.TLFunction function) {
        return sendCachedRX(function)
                .observeOn(mainThread());
    }

    //observe function
    public Observable<TLObject> sendCachedRX(final TdApi.TLFunction function) {
        return sendRx(function)
                .cache();
    }

    public Observable<TLObject> sendRx(final TdApi.TLFunction function) {
        return Observable.create(new Observable.OnSubscribe<TLObject>() {
            @Override
            public void call(final Subscriber<? super TLObject> s) {

                client.send(function, new Client.ResultHandler() {
                    @Override
                    public void onResult(TLObject object) {
                        if (object instanceof TdApi.Error) {
                            Log.e("RxClient", ((TdApi.Error) object).text);
                            s.onError(new RxClientException((TdApi.Error) object));
                        } else {
                            s.onNext(object);
                            s.onCompleted();
                        }
                    }
                });
            }
        });
    }

    public void sendSilently(final TdApi.TLFunction function) {
//        if (function instanceof TdApi.DownloadFile) {
//            int fileId = ((TdApi.DownloadFile) function).fileId;
//            Log.e("DownloadFile", "begin id:" + coolTagForFileId(fileId));
//        }
        client.send(function, RequestHandlerAdapter.INSTANCE);
    }

    public Observable<TdApi.User> getUser(int id) {

        return sendCachedRXUI(new TdApi.GetUser(id))
                .map(CAST_TO_USER);
    }

    public Observable<TdApi.GroupChatFull> getGroupChatInfo(int id) {
        return sendCachedRXUI(new TdApi.GetGroupChatFull(id))
                .map(new Func1<TLObject, TdApi.GroupChatFull>() {
                    @Override
                    public TdApi.GroupChatFull call(TLObject o) {
                        return (TdApi.GroupChatFull) o;
                    }
                });
    }

    // ui thread
    public Observable<TdApi.UpdateMessageId> messageIdsUpdates(final long chatId) {

        return globalObservableWithBackPressure.filter(ONLY_UPDATE_MESSAGE_ID)
                .map(CAST_TO_UPDATE_MESSAGE_ID)
                .filter(new Func1<TdApi.UpdateMessageId, Boolean>() {
                    @Override
                    public Boolean call(TdApi.UpdateMessageId updateMessageId) {
                        return updateMessageId.chatId == chatId;
                    }
                })
                .observeOn(mainThread());
    }

    public Observable<TdApi.UpdateMessageId> updateMessageId() {

        return globalObservableWithBackPressure.filter(ONLY_UPDATE_MESSAGE_ID)
                .map(CAST_TO_UPDATE_MESSAGE_ID);
    }

    public Observable<TdApi.UpdateMessageDate> updateMessageDate() {

        return globalObservableWithBackPressure.filter(ONLY_UPDATE_MESSAGE_DATE)
                .map(CAST_TO_UPDATE_MESSAGE_DATE);
    }

    public Observable<TdApi.UpdateChatReadInbox> updateChatReadInbox() {

        return globalObservableWithBackPressure.filter(ONLY_UPDATE_CHAT_READ_INBOX)
                .map(CAST_UPDATE_CHAT_READ_INBOX);
    }

    public Observable<TdApi.UpdateChatReadOutbox> updateChatReadOutbox() {

        return globalObservableWithBackPressure.filter(ONLY_UPDATE_CHAT_READ_OUTBOX)
                .map(CAST_UPDATE_CHAT_READ_OUTBOX);
    }

    public Observable<TdApi.UpdateDeleteMessages> updateDeleteMessages() {

        return globalObservableWithBackPressure.filter(ONLY_DELETE_MESSAGES)
                .map(CAST_TO_DELETE_MESSAGES);
    }

    public Observable<TdApi.UpdateMessageContent> updateMessageContent() {

        return globalObservableWithBackPressure.filter(ONLY_UPDATE_MESSAGE_CONTENT)
                .map(CAST_TO_UPDATE_MESSAGE_CONTENT);
    }

    public void setConnected(boolean connected) {
        boolean unreachable = !connected;
        sendSilently(new TdApi.SetOption("network_unreachable", new TdApi.OptionBoolean(unreachable)));
    }

    static class RxClientException extends Exception {
        public final TdApi.Error error;

        public RxClientException(TdApi.Error error) {
            super(error.text);
            this.error = error;
        }
    }

    //not ui thread
    public Observable<TdApi.UpdateFile> filesUpdates() {
        return fileUpdates;
    }

    public Observable<TdApi.UpdateFileProgress> fileProgress(){
        return fileProgressUpdates;
    }

    public Observable<TdApi.UpdateNewMessage> updateNewMessages() {

        return globalObservableWithBackPressure
                .filter(ONLY_NEW_MESSAGE_UPDATES)
                .map(CAST_TO_NEW_MESSAGE_UPDATE);
    }

    public Client getClient() {
        return client;
    }

    public Observable<TdApi.UpdateNotificationSettings> updateNotificationSettings() {

        return globalObservableWithBackPressure.filter(PREDICATE)
                .map(FUNC);
    }
    ////////////

    public Observable<TdApi.Chats> getChats(int offset, int limit) {
        return sendRx(new TdApi.GetChats(offset, limit))
                .map(CAST_TO_CHATS);
    }

    public Observable<TdApi.User> getMe() {
        return sendCachedRXUI(new TdApi.GetMe())
                .map(CAST_TO_USER);
    }

    public Observable<TdApi.Messages> getMessages(final long chatId, final int fromId, final int offset, final int limit) {
        return sendRx(new TdApi.GetChatHistory(chatId, fromId, offset, limit))
                .map(CAST_TO_MESSAGE);
    }
}
