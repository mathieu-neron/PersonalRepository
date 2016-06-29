package com.springapp.mongo.cascade;

import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by Mathieu on 6/29/2016.
 */
public class CascadeCallback implements ReflectionUtils.FieldCallback {

    private Object source;
    private MongoOperations mongoOperations;

    public CascadeCallback(final Object source, final MongoOperations mongoOperations) {
        this.source = source;
        this.setMongoOperations(mongoOperations);
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(CascadeSave.class)) {
            final Object fieldValue = field.get(source);
            if (fieldValue != null) {

                Class fieldClass = fieldValue.getClass();
                if (Collection.class.isAssignableFrom(field.getType())) {
                    fieldClass = field.getType();
                }

                FieldCallback fieldCallback = new FieldCallback();

                ReflectionUtils.doWithFields(fieldClass, fieldCallback);

//                if (!fieldCallback.isIdFound()) {
//                    throw new MappingException("Cannot perform cascade save on child object without id set");
//                }

                if (Collection.class.isAssignableFrom(field.getType())) {
                    @SuppressWarnings("unchecked")
                    Collection<Object> models = (Collection<Object>) fieldValue;
                    models.forEach(mongoOperations::save);
                } else {
                    mongoOperations.save(fieldValue);
                }
            }
        }
    }

    public Object getSource() {
        return source;
    }

    public MongoOperations getMongoOperations() {
        return mongoOperations;
    }

    public void setMongoOperations(final MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
}
