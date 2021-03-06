package com.vividsolutions.jtstest.function;

import java.util.List;

import com.vividsolutions.jts.geom.*;

public class FunctionsUtil {

	public static final Envelope DEFAULT_ENVELOPE = new Envelope(0, 100, 0, 100);
	
	public static Envelope getEnvelopeOrDefault(Geometry g)
	{
		if (g == null) return DEFAULT_ENVELOPE;
		return g.getEnvelopeInternal();
	}
	
	public static GeometryFactory getFactoryOrDefault(Geometry g)
	{
		if (g == null) return GeometryFactoryHolder.getInstance().getGeometryFactory();
		return g.getFactory();
	}
	
  public static GeometryFactory getFactoryOrDefault(Geometry g1, Geometry g2)
  {
    if (g1 != null) return g1.getFactory();
    if (g2 != null) return g2.getFactory();
    return GeometryFactoryHolder.getInstance().getGeometryFactory(); 
  }
  
  public static Geometry buildGeometry(List geoms, Geometry parentGeom)
  {
    if (geoms.size() <= 0)
      return null;
    if (geoms.size() == 1) 
      return (Geometry) geoms.get(0);
    // if parent was a GC, ensure returning a GC
    if (parentGeom != null && parentGeom.getGeometryType().equals("GeometryCollection"))
      return parentGeom.getFactory().createGeometryCollection(GeometryFactory.toGeometryArray(geoms));
    // otherwise return MultiGeom
    return getFactoryOrDefault(parentGeom).buildGeometry(geoms);
  }
  
  public static Geometry buildGeometry(Geometry[] geoms)
  {
    GeometryFactory gf = GeometryFactoryHolder.getInstance().getGeometryFactory();
    if (geoms.length > 0) {
      gf = getFactoryOrDefault(geoms[0]);
    }
    return gf.createGeometryCollection(geoms);
  }
  
  public static Geometry buildGeometry(Geometry a, Geometry b) {
    Geometry[] geoms = toGeometryArray(a, b);
    return getFactoryOrDefault(a, b).createGeometryCollection(geoms);  }

  public static Geometry[] toGeometryArray(Geometry a, Geometry b) {
    int size = 0;
    if (a != null) size++;
    if (b != null) size++;
    Geometry[] geoms = new Geometry[size];
    size = 0;
    if (a != null) geoms[size++] = a;
    if (b != null) geoms[size] = b;
    return geoms;
  }
}
