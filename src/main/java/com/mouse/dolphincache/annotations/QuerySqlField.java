/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mouse.dolphincache.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates fields for SQL queries. All fields that will be involved in SQL clauses must have
 * this annotation. For more information about cache queries see {@link CacheQuery} documentation.
 * @see CacheQuery
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface QuerySqlField {
    /**
     * Specifies whether cache should maintain an index for this field or not.
     * Just like with databases, field indexing may require additional overhead
     * during updates, but makes select operations faster.
     * <p>
     * When indexing SPI and indexed field is
     * of type {@code org.locationtech.jts.geom.Geometry} (or any subclass of this class) then Ignite will
     * consider this index as spatial providing performance boost for spatial queries.
     *
     * @return {@code True} if index must be created for this field in database.
     */
    boolean index() default false;
}
