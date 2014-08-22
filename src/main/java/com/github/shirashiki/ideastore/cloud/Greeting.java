/*
 *
 * Copyright 2014 Silvio Hirashiki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.github.shirashiki.ideastore.cloud;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Object which represents a greeting. It’s annotated with {@literal @}JsonIgnoreProperties 
 * from the Jackson JSON processing library to indicate that any properties not 
 * bound in this type should be ignored.
 * @author silvio hirashiki
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Greeting {
    private long id;
    private String content;

    /**
     * Empty constructor added to support JSON to object conversion using jackson
     */
    public Greeting(){
    }
    
    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

