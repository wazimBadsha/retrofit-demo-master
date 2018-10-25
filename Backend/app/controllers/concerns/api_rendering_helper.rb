module ApiRenderingHelper
  extend ActiveSupport::Concern

  def render_errors_for(entity, status_code = :unprocessable_entity)
    response = { 'errors' => entity.errors.full_messages }
    render json: response, status: status_code
  end
end