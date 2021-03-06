package ca.sait.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

/**
 * @author bin
 * @email bin.zhang@sait.ca
 * @date 2020-07-08 23:13:21
 */
@Data
@TableName("events")
public class EventsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * event_id: This is the primary key in the table.
     */
    @TableId
    private Integer eid;
    /**
     * Event name
     */
    private String event;

    /**
     * Store the address for the image
     */
    private String image;

    /**
     * Detailed description of the event
     */
    private String description;
    /**
     * start date
     */
    private Date start;
    /**
     * end date
     */
    private Date end;
    /**
     * The status of event: active or inactive
     */
    private String status;

    /**
     * Getters and setters
     **/
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
