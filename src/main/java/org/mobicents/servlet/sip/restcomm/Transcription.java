/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.mobicents.servlet.sip.restcomm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;

import org.joda.time.DateTime;

import org.mobicents.servlet.sip.restcomm.annotations.concurrency.Immutable;

/**
 * @author quintana.thomas@gmail.com (Thomas Quintana)
 */
@Immutable public final class Transcription implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private final Sid sid;
  private final DateTime dateCreated;
  private final DateTime dateUpdated;
  private final Sid accountSid;
  private final Status status;
  private final Sid recordingSid;
  private final Integer duration;
  private final String transcriptionText;
  private final BigDecimal price;
  private final URI uri;
  
  public Transcription(final Sid sid, final DateTime dateCreated, final Sid accountSid, final Status status,
      final Sid recordingSid) {
    this(sid, dateCreated, null, accountSid, status, recordingSid, null, null, null, null);
  }

  public Transcription(final Sid sid, final DateTime dateCreated, final DateTime dateUpdated, final Sid accountSid,
      final Status status, final Sid recordingSid, final Integer duration, final String transcriptionText, final BigDecimal price,
      final URI uri) {
    super();
    this.sid = sid;
    this.dateCreated = dateCreated;
    this.dateUpdated = dateUpdated;
    this.accountSid = accountSid;
    this.status = status;
    this.recordingSid = recordingSid;
    this.duration = duration;
    this.transcriptionText = transcriptionText;
    this.price = price;
    this.uri = uri;
  }

  public Sid getSid() {
    return sid;
  }

  public DateTime getDateCreated() {
    return dateCreated;
  }

  public DateTime getDateUpdated() {
    return dateUpdated;
  }

  public Sid getAccountSid() {
    return accountSid;
  }

  public Status getStatus() {
    return status;
  }

  public Sid getRecordingSid() {
    return recordingSid;
  }

  public Integer getDuration() {
    return duration;
  }

  public String getTranscriptionText() {
    return transcriptionText;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public URI getUri() {
    return uri;
  }
  
  public enum Status {
    IN_PROGRESS("in-progress"),
    COMPLETED("completed"),
    FAILED("failed");
    
    private final String text;
    
    private Status(final String text) {
      this.text = text;
    }
    
    public static Status getStatusValue(final String text) {
      final Status[] values = values();
      for(final Status value : values) {
        if(value.toString().equals(text)) {
          return value;
        }
      }
      throw new IllegalArgumentException(text + " is not a valid status.");
    }
    
    @Override public String toString() {
      return text;
    }
  }
}
