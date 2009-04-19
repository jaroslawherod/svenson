package org.svenson.tokenize;


/**
 * String implementation for the {@link JSONCharacterSource} interface for full in-memory
 * JSON parsing.
 * 
 * @author shelmberger
 */
public class StringJSONSource
    implements JSONCharacterSource
{
    private String json;

    private int index;

    private int length;

    public StringJSONSource(String json)
    {
        this.json = json;
        this.length = json.length();
    }

    @Override
    public int nextChar()
    {
        if (index < length)
        {
            return json.charAt(index++);            
        }
        else
        {
            return -1;
        }
    }

    @Override
    public int getIndex()
    {
        return index;
    }

    @Override
    public void destroy()
    {
        // nothing to do
    }
}
