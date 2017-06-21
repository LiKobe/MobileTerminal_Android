/**
 * Copyright (c) 2014 Jilk Systems, Inc.
 * <p>
 * This file is part of the Java ROSBridge Client.
 * <p>
 * The Java ROSBridge Client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * The Java ROSBridge Client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with the Java ROSBridge Client.  If not, see http://www.gnu.org/licenses/.
 */
package ros.rosbridge.operation;

import ros.message.Message;
import ros.message.MessageType;
import ros.rosbridge.indication.AsArray;
import ros.rosbridge.indication.Indicated;
import ros.rosbridge.indication.Indicator;

@MessageType(string = "call_service")
public class CallService extends Operation {
    @Indicator
    public String service;
    @Indicated
    @AsArray
    public Message args;
    public Integer fragment_size; // use Integer for optional items
    public String compression;

    public CallService() {
    }

    public CallService(String service, Message args) {
        this.service = service;
        this.args = args;
    }
}
