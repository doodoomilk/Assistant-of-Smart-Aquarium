package com.example.cloudfinal;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobile.api.idj3rw0c1f9b.TClient;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class level extends AppCompatActivity {

    // Declare a DynamoDBMapper object
    DynamoDBMapper dynamoDBMapper;

    // table
    private TableLayout tab;
    ArrayList<ArrayList<String>> rowlist = new ArrayList<ArrayList<String>>();

    // the result from db
    PaginatedList<HackLevelDO> dbresult;

    // 下拉更新
    private SwipeRefreshLayout mRefreshLayout;

    // api
    private TClient apiClient;
    private static final String LOG_TAG = MyAdapter.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        // Create the client
        apiClient = new ApiClientFactory()
                .credentialsProvider(AWSMobileClient.getInstance().getCredentialsProvider())
                .build(TClient.class);

        Button btn_levelup = findViewById(R.id.btn_levelup);
        btn_levelup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callCloudLogic();
            }
        });

        Button btn_leveldown = findViewById(R.id.btn_leveldown);
        btn_leveldown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callCloudLogic();
            }
        });


        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            setTitle(mBundle.getString("Title"));
        }

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();

        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();

        // 用join的話就要使用try，
        // 不用join的話 也可以thread.start();單獨搬出來使用
        try{
            // make thread start
            query.start();
            // make main to wait for query thread
            // Java執行緒的Thread.join()方法可以讓目前正在執行的執行緒暫停，直到呼叫join()的執行緒都執行結束才會繼續執行
            query.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        createUser();

        handlejson(dbresult);
        createTable();

        //下拉刷新
        mRefreshLayout = findViewById(R.id.refresh_layout_level);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh() {
                //先清空list
                rowlist.clear();
                tab.removeAllViews();

                // 用join的話就要使用try，
                // 不用join的話 也可以thread.start();單獨搬出來使用
                try{

                    query.join();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                handlejson(dbresult);
                createTable();

                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                mRefreshLayout.setRefreshing(false);
            }
        });


    }

    // declare thread query
    Thread query= new Thread(new Runnable() {
        @Override
        public void run() {
            HackLevelDO hackLevelDO = new HackLevelDO();
            hackLevelDO.setPrimary("p");
            hackLevelDO.setTimeStamp("2019-01");

            //The following example code shows querying for news submitted
            // with userId (hash key) and article ID beginning with Trial (range key).
            Condition rangeKeyCondition = new Condition()
                    .withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                    .withAttributeValueList(new AttributeValue().withS("2019-"));


            // greater than 100w
            //Condition rangeKeyCondition = new Condition()
            //        .withComparisonOperator(ComparisonOperator.GE)
            //        .withAttributeValueList(new AttributeValue().withN("20.0"));

            // hashkey 是看上面setUserId決定
            DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                    .withHashKeyValues(hackLevelDO)
                    .withRangeKeyCondition("timeStamp", rangeKeyCondition)
                    .withConsistentRead(false);

            dbresult = dynamoDBMapper.query(HackLevelDO.class, queryExpression);


            if (dbresult.isEmpty()) {
                Log.e("Query result", "empty");
                // There were no items matching your query.
            }
        }
    });

    public void handlejson(PaginatedList<HackLevelDO> paginatedList){

        //Gson就是Google開發用來解析JSON格式資料的應用工具，主要負責將JSON字串與JAVA物件做兩者之間的轉換
        Gson gson = new Gson();

        // ROW的第一列 是ＴＩＴＬＥ
        ArrayList<String> title = new ArrayList<String>();
        title.add("ID");
        title.add("level");
        title.add("timeStamp");
        rowlist.add(title);

        // Loop through query results
        for (int i = 0; i < paginatedList.size(); i++) {
            Log.e("i", Integer.toString(i+1));
            String jsonFormOfItem = gson.toJson(paginatedList.get(i));
            try {

                JSONObject obj = new JSONObject(jsonFormOfItem);
                String ID = Integer.toString(i+1);
                String level = obj.getString("level");
                String timeStamp = obj.getString("timeStamp");

                // 0,.換掉成空白，再去掉頭尾的空白
                //temperature = temperature.replace("0"," ").replace("."," ").trim();

                ArrayList<String> tmp_array = new ArrayList<String>();
                tmp_array.add(ID);
                tmp_array.add(level);
                tmp_array.add(timeStamp);

                rowlist.add(tmp_array);

                //Log.e("My App", obj.toString());
                Log.e("attribute:", "ID is " + ID +", level is " + level + ", timeStamp is " + timeStamp);

            } catch (Throwable e) {
                e.printStackTrace();
            }

        }

    }

    public void createTable(){
        // set table
        tab = findViewById(R.id.table_level);

        for (int row = 0; row < rowlist.size(); row++) {
            TableRow tabRow = new TableRow(this);

            for (int col = 0 ; col<rowlist.get(row).size(); col++){
                TextView tv = new TextView(this);
                if (col == 1 && row !=0) {
                    if (Double.parseDouble(rowlist.get(row).get(col)) < 20){
                        tv.setTextColor(Color.RED);
                        tv.setTypeface(Typeface.DEFAULT_BOLD);
                    }
                }
                tv.setText(rowlist.get(row).get(col));
                tv.setBackgroundResource(R.color.colorbackground);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(3,3,3,3);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 50);
                if (row==0 && col==0){
                    params.setMargins(2,2,2,2);
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                }
                else if (row==0 && col!=0){
                    params.setMargins(0,2,2,2);
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                }
                else if (row!=0 && col==0){
                    params.setMargins(1,0,1,1);
                }
                else{
                    params.setMargins(0,0,1,1);
                }
                tv.setLayoutParams(params);
                tabRow.addView(tv);
            }
            tab.addView(tabRow);
        }
    }


    public void createUser() {
        final HackLevelDO incomeItem = new HackLevelDO();

        incomeItem.setPrimary("p");

        incomeItem.setTimeStamp("2019-06-06 11:11:11");
        incomeItem.setLevel(5.0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(incomeItem);

                // Item saved
                Log.e("Item:", "saved");
            }
        }).start();
    }


    public void callCloudLogic() {
        // Create components of api request
        final String method = "GET";

        final String path = "/test";

        final String body = "";
        final byte[] content = body.getBytes(StringUtils.UTF8);

        final Map parameters = new HashMap<>();
        parameters.put("lang", "en_US");

        final Map headers = new HashMap<>();

        // Use components to create the api request
        ApiRequest localRequest =
                new ApiRequest(apiClient.getClass().getSimpleName())
                        .withPath(path)
                        .withHttpMethod(HttpMethodName.valueOf(method))
                        .withHeaders(headers)
                        .addHeader("Content-Type", "application/json")
                        .withParameters(parameters);

        // Only set body if it has content.
        if (body.length() > 0) {
            localRequest = localRequest
                    .addHeader("Content-Length", String.valueOf(content.length))
                    .withBody(content);
        }

        final ApiRequest request = localRequest;

        // Make network call on background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(LOG_TAG,
                            "Invoking API w/ Request : " +
                                    request.getHttpMethod() + ":" +
                                    request.getPath());

                    final ApiResponse response = apiClient.execute(request);

                    final InputStream responseContentStream = response.getContent();

                    if (responseContentStream != null) {
                        final String responseData = IOUtils.toString(responseContentStream);
                        Log.d(LOG_TAG, "Response : " + responseData);
                    }

                    Log.d(LOG_TAG, response.getStatusCode() + " " + response.getStatusText());

                } catch (final Exception exception) {
                    Log.e(LOG_TAG, exception.getMessage(), exception);
                    exception.printStackTrace();
                }
            }
        }).start();
    }

}
