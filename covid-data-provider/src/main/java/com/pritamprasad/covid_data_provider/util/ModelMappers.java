package com.pritamprasad.covid_data_provider.util;

import com.pritamprasad.covid_data_provider.models.BaseResponse.Counts;
import com.pritamprasad.covid_data_provider.models.DistrictResponse;
import com.pritamprasad.covid_data_provider.models.LocationEntity;
import com.pritamprasad.covid_data_provider.models.MetaDataEntity;
import com.pritamprasad.covid_data_provider.models.StateResponse;

public class ModelMappers {

    public static DistrictResponse getDistrictResponseFromLocationEntity(final LocationEntity entity) {
        final DistrictResponse response = new DistrictResponse();
        response.setName(entity.getName());
        response.setStateId(entity.getParentId());
        response.setId(entity.getId());
        return response;
    }

    public static StateResponse getStateResponseFromLocationEntity(final LocationEntity entity) {
        final StateResponse response = new StateResponse();
        response.setName(entity.getName());
        response.setCountryId(entity.getParentId());
        response.setId(entity.getId());
        return response;
    }

    public static Counts getCountFromMeta(final MetaDataEntity m) {
        final Counts counts = new Counts();
        counts.setConfirmed(m.getConfirmed() == null ? 0 : m.getConfirmed());
        counts.setDeceased(m.getDeceased() == null ? 0 : m.getDeceased());
        counts.setRecovered(m.getRecovered() == null ? 0 : m.getRecovered());
        counts.setTested(m.getTested() == null ? 0 : m.getTested());
        counts.setDate(m.getCreatedDate());
        return counts;
    }

}
