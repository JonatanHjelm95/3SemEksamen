import React from 'react';
import './styles.scss';

/*
Dev notes on DBA
på DBA kan der findes den box der hedder vip-aditional text som indeholder product beskrivelsen
vip-qna er info om varens stand.
vip-matrix indeholder metadata om produktet. TAGS ex.
vip-picure-galery har en masse billeder
*/

/*
Box with descrptive text and an image.
Needs to be initialised with: 
-src
the images src.
-title
whatever title is needed for the component to make sence.
-description
description for the picture or component.
*/

class DescriptionBox extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      description: '',
    };
  }

  render() {
    const {
      obj: { imgurl, title, description, url },
    } = this.props;

    return (
      <div className="DescriptionBox">
        <div className="container-left">
          <img
            src={imgurl}
            className="image"
            alt="Billede ikke tilgængeligt."
          />
          <a href={url} target="_blank" className="link">
            Link til Siden
          </a>
        </div>

        <div className="container-middle">
          <p className="title">{title}</p>
          <hr className="custom-hr" />
          <p className="description">{description}</p>
        </div>

        <div className="container-right"></div>
      </div>
    );
  }
}

export default DescriptionBox;
