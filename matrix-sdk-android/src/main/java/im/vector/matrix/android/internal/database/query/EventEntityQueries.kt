/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.matrix.android.internal.database.query

import im.vector.matrix.android.internal.database.model.EventEntity
import im.vector.matrix.android.internal.database.model.EventEntity.LinkFilterMode.*
import im.vector.matrix.android.internal.database.model.EventEntityFields
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmQuery
import io.realm.Sort
import io.realm.kotlin.where

internal fun EventEntity.Companion.where(realm: Realm, eventId: String): RealmQuery<EventEntity> {
    return realm.where<EventEntity>()
            .equalTo(EventEntityFields.EVENT_ID, eventId)
}

internal fun EventEntity.Companion.where(realm: Realm, eventIds: List<String>): RealmQuery<EventEntity> {
    return realm.where<EventEntity>()
            .`in`(EventEntityFields.EVENT_ID, eventIds.toTypedArray())
}

internal fun EventEntity.Companion.whereType(realm: Realm,
                                             type: String,
                                             roomId: String? = null
): RealmQuery<EventEntity> {
    val query = realm.where<EventEntity>()
    if (roomId != null) {
        query.equalTo(EventEntityFields.ROOM_ID, roomId)
    }
    return query.equalTo(EventEntityFields.TYPE, type)
}

internal fun EventEntity.Companion.types(realm: Realm,
                                         typeList: List<String> = emptyList()): RealmQuery<EventEntity> {
    val query = realm.where<EventEntity>()
    query.`in`(EventEntityFields.TYPE, typeList.toTypedArray())
    return query
}

internal fun RealmList<EventEntity>.find(eventId: String): EventEntity? {
    return this.where()
            .equalTo(EventEntityFields.EVENT_ID, eventId)
            .findFirst()
}

internal fun RealmList<EventEntity>.fastContains(eventId: String): Boolean {
    return this.find(eventId) != null
}
