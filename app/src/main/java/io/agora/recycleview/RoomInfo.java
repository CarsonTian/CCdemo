package io.agora.recycleview;

/**
 * Created by tianjq1 on 2017/12/11.
 */

public class RoomInfo
{
    private String name;
    private String people;
    private String time;
    private boolean lock;

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getTime()
    {
        return time;
    }
    public void setTime(String time)
    {
        this.time = time;
    }

    public String getPeople()
    {
        return people;
    }
    public void setPeople(String people)
    {
        this.people = people;
    }

    public boolean getLock() {return lock;}
    public void setLock(boolean lock)
    {
        this.lock = lock;
    }
}
