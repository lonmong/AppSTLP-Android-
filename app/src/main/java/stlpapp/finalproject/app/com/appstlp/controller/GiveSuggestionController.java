package stlpapp.finalproject.app.com.appstlp.controller;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import stlpapp.finalproject.app.com.appstlp.R;
import stlpapp.finalproject.app.com.appstlp.model.AssignModel;
import stlpapp.finalproject.app.com.appstlp.model.RequestForHelpModel;
import stlpapp.finalproject.app.com.appstlp.task.WSTask;


public class GiveSuggestionController {
    private static GiveSuggestionController wsManager;
    private Context context;

    public interface GiveSuggestionControllerListener {
        void onComplete(Object response);

        void onError(String err);
    }

    public GiveSuggestionController(Context context) {
        this.context = context;
    }

    public static GiveSuggestionController getWsManager(Context context) {
        if (wsManager == null)
            wsManager = new GiveSuggestionController(context);
        return wsManager;
    }

    public void detailRequestByIdRequest(int idrequest, final GiveSuggestionControllerListener listener) {

        WSTask task = new WSTask(this.context,new WSTask.WSTaskListener() {
            @Override
            public void onComplete(String response) {
                RequestForHelpModel requestForHelpModel = new RequestForHelpModel(response);
                listener.onComplete(requestForHelpModel);
            }

            @Override
            public void onError(String err) {
                listener.onError(err);
            }
        });
        Gson gson = new GsonBuilder().create();
        String idrequestjson = gson.toJson(idrequest);
        task.execute(context.getString(R.string.getdetailrequestbyidrequest_url), idrequestjson);

    }

    public void setSuggestionRequest(Object object, final WSManager.WSManagerListener listener) {
        if (!(object instanceof AssignModel)) {
            return;
        }
        AssignModel assignModel = (AssignModel) object;
        assignModel.toJSONString();
        WSTask task = new WSTask(this.context,new WSTask.WSTaskListener() {
            @Override
            public void onComplete(String response) {
                AssignModel assignModel1 = new AssignModel(response);
                listener.onComplete(assignModel1);

            }

            @Override
            public void onError(String err) {
                listener.onError(err);
            }
        });
        task.execute(context.getString(R.string.givesuggestion_url), assignModel.toJSONString());
    }

}
