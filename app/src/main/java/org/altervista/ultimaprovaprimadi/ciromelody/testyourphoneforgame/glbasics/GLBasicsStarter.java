package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GLBasicsStarter extends ListActivity {
    String tests[] = { "GLSurfaceViewTest", "GLGameTest", "GLPrimoTriangolo",
                       "ColoredTriangleTest", "TexturedTriangleTest", "IndexedTest", 
                       "BlendingTest", "BobTest", "OptimizedBobTest"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tests));
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position,long id) {
        super.onListItemClick(list, view, position, id);
        String testName = tests[position];
        try {
            Class clazz = Class
                    .forName("org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics." + testName);
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
            //finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}