package com.intranet.api.intranet.repositories;

import com.intranet.api.intranet.models.entities.DetalleLM;
import com.intranet.api.intranet.models.entities.LicienciaMedica;

public interface IDetalleLMRepository {

    DetalleLM buscaDetalleLm(LicienciaMedica licienciaMedica);

}
