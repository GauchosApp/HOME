package m2wapps.ar.ifitweremyhome;

/**
 * Created by mariano on 27/02/2017.
 */

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    private List<Datos> datos;
    // child data in format of header title, child title
    private HashMap<String, String> _listDataChild;

    ExpandableListAdapter(Context context, List<String> listDataHeader,
                          HashMap<String, String> listChildData, List<Datos> datos) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.datos = datos;
    }
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
               ;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        if(datos.get(groupPosition).getTipo() == 0) {
            txtListChild.setBackgroundColor(_context.getResources().getColor(R.color.negative));
        }else  if(datos.get(groupPosition).getTipo() == 1) {
            txtListChild.setBackgroundColor(_context.getResources().getColor(R.color.positive));
        }else{
            txtListChild.setBackgroundColor(_context.getResources().getColor(R.color.neutral));
        }
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        if(datos.get(groupPosition).getTipo() == 0) {
            lblListHeader.setBackgroundColor(_context.getResources().getColor(R.color.negative));
        }else  if(datos.get(groupPosition).getTipo() == 1) {
            lblListHeader.setBackgroundColor(_context.getResources().getColor(R.color.positive));
        }else{
            lblListHeader.setBackgroundColor(_context.getResources().getColor(R.color.neutral));
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
