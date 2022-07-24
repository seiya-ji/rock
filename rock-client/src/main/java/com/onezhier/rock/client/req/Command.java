/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.client.req;


import com.onezhier.rock.client.Request;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>Title: Command </p>
 * <p>
 * BizScenario（业务场景）= bizId + useCase + scenario, which can uniquely identify a user scenario.
 * </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Command extends Request {

    private static final long serialVersionUID = 1L;

}
