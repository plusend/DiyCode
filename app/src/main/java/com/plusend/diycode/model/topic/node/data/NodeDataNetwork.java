package com.plusend.diycode.model.topic.node.data;

import android.util.Log;
import com.plusend.diycode.model.topic.node.entity.Node;
import com.plusend.diycode.model.topic.node.event.NodesEvent;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NodeDataNetwork implements NodeData {
    private static final String TAG = "NetworkData";
    private static NodeDataNetwork networkData = new NodeDataNetwork();
    private NodeService service;

    private NodeDataNetwork() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.diycode.cc/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        service = retrofit.create(NodeService.class);
    }

    public static NodeDataNetwork getInstance() {
        return networkData;
    }

    @Override public void readNodes() {
        Call<List<Node>> call = service.readNodes();
        call.enqueue(new Callback<List<Node>>() {
            @Override public void onResponse(Call<List<Node>> call, Response<List<Node>> response) {
                if (response.isSuccessful()) {
                    List<Node> nodeList = response.body();
                    Log.v(TAG, "nodeList:" + nodeList);
                    EventBus.getDefault().postSticky(new NodesEvent(nodeList));
                } else {
                    Log.e(TAG, "readNodes STATUS: " + response.code());
                    EventBus.getDefault().postSticky(new NodesEvent(null));
                }
            }

            @Override public void onFailure(Call<List<Node>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().postSticky(new NodesEvent(null));
            }
        });
    }
}
