package com.intranet.api.intranet.repositories;

import com.intranet.api.intranet.models.entities.DetalleLM;

public interface IDetalleLMRepository {

    DetalleLM buscaDetalleLm(Long numLic);

}
