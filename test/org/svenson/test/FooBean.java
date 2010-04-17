/**
 * 
 */
package org.svenson.test;

import java.util.ArrayList;
import java.util.List;

public class FooBean
{
    private List<Bar> bars = new ArrayList<Bar>();

    public void addBar(Bar bar)
    {
        bars.add(bar);
    }

    public List<Bar> getBars()
    {
        return bars;
    }
    
    public void setBars(List<Bar> bars)
    {
        this.bars = bars;
    }
    
}