import React from 'react';
import './InfoTile.css';

const { Card, Typography, CardContent, Divider } = require("@material-ui/core");

function InfoTile({ data }) {
    return (
        <React.Fragment>
            <Card className="infoTilesRoot">
                <CardContent className="infoTiles">
                    <Typography className="infoTitleCount" align="center" variant="h5" color="primary">
                        {data.count}
                    </Typography>
                    <Divider light={true}/>
                    <Typography className="infoTitleHeader" align="center" variant="h6" color="textSecondary" >
                        {data.name}
                    </Typography>
                </CardContent>
            </Card>
        </React.Fragment>
    );
}

export default InfoTile;