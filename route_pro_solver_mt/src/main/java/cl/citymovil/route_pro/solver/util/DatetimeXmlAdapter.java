package cl.citymovil.route_pro.solver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DatetimeXmlAdapter extends XmlAdapter<String, Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    public String marshal(Date datetime) throws Exception {
        return dateFormat.format(datetime);
    }

    @Override
    public Date unmarshal(String datetime) throws Exception {
        return dateFormat.parse(datetime);
    }

}