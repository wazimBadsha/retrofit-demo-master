class Api::ApiController < ActionController::Base
  protect_from_forgery with: :null_session
  include ApiRenderingHelper
  respond_to :json, :xml
end
