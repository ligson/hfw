package org.ligson.fw.core.common.idgenerator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.ligson.fw.core.common.utils.IdUtils;

import java.io.Serializable;

/**
 * Created by ligson on 2016/4/27.
 */
public class DateRandomGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        return IdUtils.randomBigInt();
    }
}
