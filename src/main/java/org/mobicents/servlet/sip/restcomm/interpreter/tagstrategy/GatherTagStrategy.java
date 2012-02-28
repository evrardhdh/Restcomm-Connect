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
package org.mobicents.servlet.sip.restcomm.interpreter.tagstrategy;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.mobicents.servlet.sip.restcomm.callmanager.Call;
import org.mobicents.servlet.sip.restcomm.interpreter.TagStrategyException;
import org.mobicents.servlet.sip.restcomm.interpreter.RcmlInterpreter;
import org.mobicents.servlet.sip.restcomm.interpreter.RcmlInterpreterContext;
import org.mobicents.servlet.sip.restcomm.xml.IntegerAttribute;
import org.mobicents.servlet.sip.restcomm.xml.Tag;
import org.mobicents.servlet.sip.restcomm.xml.UriAttribute;
import org.mobicents.servlet.sip.restcomm.xml.rcml.Action;
import org.mobicents.servlet.sip.restcomm.xml.rcml.FinishOnKey;
import org.mobicents.servlet.sip.restcomm.xml.rcml.Method;
import org.mobicents.servlet.sip.restcomm.xml.rcml.NumDigits;
import org.mobicents.servlet.sip.restcomm.xml.rcml.RCMLTag;
import org.mobicents.servlet.sip.restcomm.xml.rcml.Timeout;

public final class GatherTagStrategy extends TwiMLTagStrategy {
  public GatherTagStrategy() {
    super();
  }

  @Override public void execute(final RcmlInterpreter interpreter,
      final RcmlInterpreterContext context, final Tag tag) throws TagStrategyException {
    // Try to answer the call if it hasn't been done so already.
    final Call call = context.getCall();
	answer(call);
    // Start gathering digits.
    try {
    final URI action = ((UriAttribute)tag.getAttribute(Action.NAME)).getUriValue();
    final String method = tag.getAttribute(Method.NAME).getValue();
    final String finishOnKey = tag.getAttribute(FinishOnKey.NAME).getValue();
    final int numDigits = ((IntegerAttribute)tag.getAttribute(NumDigits.NAME)).getIntegerValue();
    final int timeout = ((IntegerAttribute)tag.getAttribute(Timeout.NAME)).getIntegerValue();
    final List<URI> announcements = getAnnouncements(tag.getChildren());
    final String digits = call.playAndCollect(announcements, finishOnKey, numDigits, timeout);
    final List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    parameters.add(new BasicNameValuePair("Digits", digits));
    interpreter.loadResource(action, method, parameters);
    interpreter.redirect();
    } catch(final Exception exception) {
      throw new TagStrategyException(exception);
    }
  }
  
  private List<URI> getAnnouncements(final List<Tag> children) {
	final List<URI> announcements = new ArrayList<URI>();
    for(final Tag child : children) {
 	  ((RCMLTag)child).setHasBeenVisited(true);
 	}
    return announcements;
  }
}
